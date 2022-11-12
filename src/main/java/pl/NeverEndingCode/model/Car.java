package pl.NeverEndingCode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.NeverEndingCode.enums.car.BodyType;
import pl.NeverEndingCode.enums.car.EngineType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CARS")
public class Car extends Vehicle {
    @Column(name = "ENGINE_TYPE")
    private EngineType engineType;
    @Column(name = "BODY_TYPE")
    private BodyType bodyType;
    @Column(name = "NUMBER_OF_SEATS")
    private int numberOfSeats;

    public void updateFrom(final Car source) {
        super.updateFrom(source);
        this.engineType = source.engineType;
        this.bodyType = source.bodyType;
        this.numberOfSeats = source.numberOfSeats;
    }

}
