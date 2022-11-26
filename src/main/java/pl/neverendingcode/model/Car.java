package pl.neverendingcode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.neverendingcode.enums.car.BodyType;
import pl.neverendingcode.enums.car.EngineType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car extends Vehicle {
    @Column(name = "engine_type")
    @Enumerated(EnumType.STRING)
    private EngineType engineType;
    @Column(name = "body_type")
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;
    @Column(name = "number_of_seats")
    private int numberOfSeats;
    @Embedded
    private Audit audit = new Audit();

    public void updateFrom(final Car source) {
        super.updateFrom(source);
        this.engineType = source.engineType;
        this.bodyType = source.bodyType;
        this.numberOfSeats = source.numberOfSeats;
    }

}
