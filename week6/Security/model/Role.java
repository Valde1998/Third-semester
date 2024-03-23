package Security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@Setter
@Entity
@NamedQueries(@NamedQuery(name = "User.deleteAllRows", query = "DELETE FROM User"))
@Table(name = "role")
@NoArgsConstructor
public class Role implements Serializable {
    @Id
    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public Role(String roleName) {
        this.name = roleName;
    }

    public String getRoleName() {
        return name;
    }
    public Set<User> getUsers() {
        return users;
    }

}