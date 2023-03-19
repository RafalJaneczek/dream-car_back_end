package pl.neverendingcode.vehicle.car.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.vehicle.car.enums.BodyType;
import pl.neverendingcode.vehicle.car.enums.EngineType;
import pl.neverendingcode.vehicle.entity.Vehicle;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a car entity.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_cars")
public class Car extends Vehicle {

    /**
     * The type of engine used in the car.
     */
    @Column(name = "engine_type")
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Engine type must not be empty")
    private EngineType engineType;

    /**
     * The type of body used in the car.
     */
    @Column(name = "body_type")
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Body type must not be empty")
    private BodyType bodyType;

    /**
     * The number of seats in the car.
     */
    @Column(name = "number_of_seats")
    @NotNull(message = "Number of seats must not be empty")
    private int numberOfSeats;

    /**
     * List of the car photos
     */
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarPhoto> carPhotos = new ArrayList<>();

    /**
     * Updates car photo collection.
     *
     * @param carPhoto instance
     */
    public void addPhoto(CarPhoto carPhoto) {
        carPhoto.setCar(this);
        this.carPhotos.add(carPhoto);
    }

    /**
     * Updates the fields of this car from another car.
     *
     * @param source the source car to update from
     */
    public void updateFrom(final Vehicle source, final List<CarPhoto> carPhotos) {
        super.updateFrom(source);
        if (source instanceof final Car carSource) {
            this.engineType = carSource.engineType;
            this.bodyType = carSource.bodyType;
            this.numberOfSeats = carSource.numberOfSeats;
            carPhotos.forEach(this::addPhoto);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        if (!super.equals(o)) return false;
        return numberOfSeats == car.numberOfSeats && engineType == car.engineType && bodyType == car.bodyType && Objects.equals(carPhotos, car.carPhotos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), engineType, bodyType, numberOfSeats, carPhotos);
    }
}
