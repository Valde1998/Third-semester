package Security.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TokenDTO {

    String token;
    String username;
}
