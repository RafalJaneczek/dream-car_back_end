package pl.neverendingcode.vehicle.car.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.vehicle.entity.Audit;
import pl.neverendingcode.vehicle.entity.Vehicle;
import pl.neverendingcode.vehicle.car.enums.BodyType;
import pl.neverendingcode.vehicle.car.enums.EngineType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "cars")
public class Car extends Vehicle {

    @Column(name = "engine_type")
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Engine type must not be empty")
    private EngineType engineType;

    @Column(name = "body_type")
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Body type must not be empty")
    private BodyType bodyType;

    @Column(name = "number_of_seats")
    @NotNull(message = "Number of seats must not be empty")
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
