package Dolphinexercise;


import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class PersonDTO {

    private int id;
    private String name;

    private int amount;
    private LocalDate payDate;

    public PersonDTO(int id, String name, int amount, LocalDate payDate) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.payDate = payDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", payDate=" + payDate +
                '}';
    }
}
