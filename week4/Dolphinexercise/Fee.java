package Dolphinexercise;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Fee
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private int amount;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDate payDate;

    @ManyToOne
    @ToString.Exclude
    private Person person;

    public Fee(int amount, LocalDate payDate)
    {
        this.amount = amount;
        this.payDate = payDate;
    }

}
