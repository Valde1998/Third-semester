package DAOExercise;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "age")
    private int age;

    public Student(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    @PrePersist
    public void validateEmail() {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    @PreUpdate
    public void validateEmailOnUpdate() {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

}
