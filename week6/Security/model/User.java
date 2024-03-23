package Security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@ToString
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    private String password;

    @ManyToMany
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();


    public User (String username, String password){
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String pw){

        return BCrypt.checkpw(pw, this.password);
    }

    public Set<String> getRolesAsString(){

        if (roles.isEmpty()) {
            return null;
        }
        Set<String> rolesAsStrings = new HashSet<>();
        roles.forEach((role) -> {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }


}