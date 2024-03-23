package Security.controllers;

import Security.dtos.TokenDTO;
import Security.dtos.UserDTO;
import Security.exceptions.ApiException;
import Security.exceptions.NotAuthorizedException;
import Security.exceptions.ValidationException;
import Security.model.User;
import Security.persistence.daos.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;


import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class SecurityController implements ISecurityController{

    private static SecurityController instance;

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static UserDao userDao = new UserDao();

    private final String SECRET_KEY = "DetteErEnHemmeligNøgleTilAtDanneJWT_TokensMed";


    public static SecurityController getInstance() {
        if (instance == null) {
            instance = new SecurityController();
        }
        return instance;
    }

    public Handler register() {
        return ctx -> {
            ObjectNode returnObject = objectMapper.createObjectNode();

            try {
                UserDTO userInput = ctx.bodyAsClass(UserDTO.class);
                User created = userDao.createUser(userInput.getUsername(), userInput.getPassword(), userInput.getRoles());

                String token = createToken(new UserDTO(created));
                ctx.status(HttpStatus.CREATED).json(new TokenDTO(token, userInput.getUsername()));

            } catch (EntityExistsException e) {
                ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
                ctx.json(returnObject.put("msg", "User already exists"));
            }
        };
    }

    public Handler login() {

        return ctx -> {
            ObjectNode returnObject = objectMapper.createObjectNode();

            try {
                UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);

                User verifiedUser = userDao.getVerifiedUser(userDTO.getUsername(), userDTO.getPassword());
                String token = createToken(new UserDTO(verifiedUser));
                ctx.status(200).json(new TokenDTO(token, userDTO.getUsername()));

            } catch (EntityNotFoundException | ValidationException e) {
                ctx.status(401);
                System.out.println(e.getMessage());
                ctx.json(returnObject.put("msg", e.getMessage()));
            }
        };
    }


    public String createToken(UserDTO userDTO) {

        String ISSUER;
        String TOKEN_EXPIRE_TIME;
        String SECRET_KEY;

        if (System.getenv("DEPLOYED") != null)
            {
                ISSUER = System.getenv("ISSUER");
                TOKEN_EXPIRE_TIME = System.getenv("TOKEN_EXPIRE_TIME");
                SECRET_KEY = System.getenv("SECRET_KEY");

            } else{
                ISSUER = "Thomas Hartmann";
                TOKEN_EXPIRE_TIME = "1800000"; // 30 minutes in milliseconds
                SECRET_KEY = this.SECRET_KEY;
            }
            return createTokenAsString(userDTO, ISSUER, TOKEN_EXPIRE_TIME, SECRET_KEY);
        }

    private String createTokenAsString(UserDTO userDTO, String ISSUER, String TOKEN_EXPIRE_TIME, String SECRET_KEY) {

        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userDTO.getUsername())
                    .issuer(ISSUER)
                    .claim("username", userDTO.getUsername())
                    .claim("roles", userDTO.getRoles().stream().reduce("", (s1, s2) -> s1 + "," + s2))
                    .expirationTime(new Date(new Date().getTime() + Integer.parseInt(TOKEN_EXPIRE_TIME)))
                    .build();
            Payload payload = new Payload(claimsSet.toJSONObject());

            JWSSigner signer = new MACSigner(SECRET_KEY);
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);

            jwsObject.sign(signer);

            return jwsObject.serialize();

        } catch (JOSEException e) {
            e.printStackTrace();
            throw new ApiException(500, "Could not create token");
        }
    }

    public boolean authorize (UserDTO user, Set < String > allowedRoles){

            AtomicBoolean hasAccess = new AtomicBoolean(false);

            if (user != null) {
                user.getRoles().stream().forEach(role -> {
                    if (allowedRoles.contains(role.toUpperCase())) {
                        hasAccess.set(true);
                    }
                });
            }
            return hasAccess.get();
        }

    public Handler authenticate() {
        // To check the users roles against the allowed roles for the endpoint (managed by javalins accessManager)
        // Checked in 'before filter' -> Check for Authorization header to find token.
        // Find user inside the token, forward the ctx object with userDTO on attribute
        // When ctx hits the endpoint it will have the user on the attribute to check for roles (ApplicationConfig -> accessManager)

        ObjectNode returnObject = objectMapper.createObjectNode();

        return (ctx) -> {
            if(ctx.method().toString().equals("OPTIONS")) {
                ctx.status(200);
                return;
            }
            String header = ctx.header("Authorization");
            if (header == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Authorization header missing"));
                return;
            }
            String token = header.split(" ")[1];
            if (token == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Authorization header malformed"));
                return;
            }
            UserDTO verifiedTokenUser = verifyToken(token);

            if (verifiedTokenUser == null) {
                ctx.status(HttpStatus.FORBIDDEN).json(returnObject.put("msg", "Invalid User or Token"));
            }
            System.out.println("USER IN AUTHENTICATE: " + verifiedTokenUser);
            ctx.attribute("user", verifiedTokenUser);
        };
    }


    public UserDTO verifyToken(String token) {

        boolean IS_DEPLOYED = (System.getenv("DEPLOYED") != null);

        String SECRET_K = IS_DEPLOYED ? System.getenv("SECRET_KEY") : SECRET_KEY;

        try {
            if (tokenIsValid(token, SECRET_K) && tokenNotExpired(token)) {
                return getUserWithRolesFromToken(token);
            } else {
                throw new NotAuthorizedException(403, "Token is not valid");
            }
        } catch (ParseException | JOSEException | NotAuthorizedException e) {
            e.printStackTrace();
            throw new ApiException(HttpStatus.UNAUTHORIZED.getCode(), "Unauthorized. Could not verify token");
        }
    }
    public boolean tokenIsValid(String token, String secret) throws ParseException, JOSEException, NotAuthorizedException{
        SignedJWT jwt = SignedJWT.parse(token);

        if (jwt.verify(new MACVerifier(secret)))
            return true;
        else
            throw new NotAuthorizedException(403, "Token is not valid");
    }
    public boolean tokenNotExpired(String token) throws ParseException, NotAuthorizedException {
        if (timeToExpire(token) > 0)
            return true;
        else
            throw new NotAuthorizedException(403, "Token has expired");
    }
    public UserDTO getUserWithRolesFromToken(String token) throws ParseException {
        // Return a user with Set of roles as strings
        SignedJWT jwt = SignedJWT.parse(token);
        String roles = jwt.getJWTClaimsSet().getClaim("roles").toString();
        String username = jwt.getJWTClaimsSet().getClaim("username").toString();

        Set<String> rolesSet = Arrays
                .stream(roles.split(","))
                .collect(Collectors.toSet());
        return new UserDTO(username, rolesSet);
    }
    public int timeToExpire(String token) throws ParseException, NotAuthorizedException {
        SignedJWT jwt = SignedJWT.parse(token);
        return (int) (jwt.getJWTClaimsSet().getExpirationTime().getTime() - new Date().getTime());
    }

}
