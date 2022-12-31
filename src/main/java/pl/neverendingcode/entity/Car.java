package pl.neverendingcode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.enums.car.BodyType;
import pl.neverendingcode.enums.car.EngineType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
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
