package UnicornEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name ="unicorn")
@NoArgsConstructor
@Getter
@ToString
@Setter
public class Unicorn {

    @PrePersist
    public void prePersist() throws Exception {
        this.name = name.toLowerCase();

        if(this.powerStrength > 100 || this.powerStrength <= 0){
            throw new Exception("Error while persisting, powerStrength cannot be equal to: "+this.powerStrength);
        }

    }
    @PreUpdate
    public void preUpdate() throws Exception{
        this.name = name.toLowerCase();

        if(this.powerStrength > 100 || this.powerStrength <= 0){
            throw new Exception("Error while persisting, powerStrength cannot be equal to: "+this.powerStrength);
        }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name ="first_name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "power_strength", nullable = false, length = 100)
    private int powerStrength;


    public Unicorn(String name, int age, int powerStrength){
        this.name = name;
        this.age = age;
        this.powerStrength = powerStrength;
    }

}