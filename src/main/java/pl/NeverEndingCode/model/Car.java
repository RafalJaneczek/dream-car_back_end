package pl.NeverEndingCode.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CAR")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String model;
    private String type;
    private String plate;
    private String deliveryDate;
    private String deadline;
    private String color;
    private int power;
    private String clientFirstName;
    private String clientSurname;
    private int cost;
    private boolean isFullyDamaged;
    private int year;

    public void updateFrom(final Car source) {
        this.model = source.model;
        this.type = source.type;
        this.plate = source.plate;
        this.deliveryDate = source.deliveryDate;
        this.deadline = source.deadline;
        this.color = source.color;
        this.power = source.power;
        this.clientFirstName = source.clientFirstName;
        this.clientSurname = source.clientSurname;
        this.cost = source.cost;
        this.isFullyDamaged = source.isFullyDamaged;
        this.year = source.year;
    }

}
