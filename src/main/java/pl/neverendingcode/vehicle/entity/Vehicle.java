package pl.neverendingcode.vehicle.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.vehicle.car.entity.Car;
import pl.neverendingcode.vehicle.enums.VehicleCondition;
import pl.neverendingcode.vehicle.motorcycle.entity.Motorcycle;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * Represents a vehicle entity.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@MappedSuperclass
@JsonSubTypes({
        @JsonSubTypes.Type(value = Car.class, name = "car"),
        @JsonSubTypes.Type(value = Motorcycle.class, name = "motorcycle")
})
public class Vehicle {

    /**
     * The id of the vehicle entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_sequence")
    @SequenceGenerator(name = "vehicle_sequence", sequenceName = "vehicle_sequence", allocationSize = 1)
    private Long id;

    /**
     * The mark of the vehicle.
     */
    @Column(name = "mark")
    @Min(message = "Mark cannot be shorter than 2 characters", value = 2)
    @NotEmpty(message = "Mark must not be empty")
    protected String mark;

    /**
     * The model of the vehicle.
     */
    @Column(name = "model")
    @Min(message = "Model cannot be shorter than 2 characters", value = 2)
    @NotEmpty(message = "Model must not be empty")
    protected String model;

    /**
     * The engine capacity of the vehicle.
     */
    @Column(name = "engine_capacity")
    @NotNull(message = "Engine capacity must not be empty")
    protected int engineCapacity;

    /**
     * The engine power of the vehicle.
     */
    @Column(name = "engine_power")
    @NotNull(message = "Engine power must not be empty")
    protected int enginePower;

    /**
     * The mileage of the vehicle.
     */
    @Column(name = "course")
    @NotNull(message = "Course must not be empty")
    protected int course;

    /**
     * The condition of the vehicle.
     */
    @Column(name = "vehicle_condition")
    @Enumerated(EnumType.STRING)
    @NotEmpty(message = "Vehicle condition must not be empty")
    protected VehicleCondition vehicleCondition;

    /**
     * Indicates whether the vehicle is damaged or not.
     */
    @Column(name = "damaged")
    @NotNull(message = "Damaged param must not be empty")
    protected boolean damaged = false;

    /**
     * The production year of the vehicle.
     */
    @Column(name = "production_year")
    @NotNull(message = "Production year must not be empty")
    private int productionYear;

    /**
     * The price of the vehicle.
     */
    @Column(name = "price")
    @NotNull(message = "Price must not be empty")
    private BigDecimal price;

    /**
     * The audit information of the vehicle.
     */
    @Embedded
    private Audit audit = new Audit();

    /**
     * Updates the fields of this vehicle from another vehicle.
     *
     * @param source the source vehicle to update from
     */
    protected void updateFrom(Vehicle source) {
        this.mark = source.mark;
        this.model = source.model;
        this.engineCapacity = source.engineCapacity;
        this.enginePower = source.enginePower;
        this.course = source.course;
        this.vehicleCondition = source.vehicleCondition;
        this.damaged = source.damaged;
        this.productionYear = source.productionYear;
        this.price = source.price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle vehicle)) return false;
        return engineCapacity == vehicle.engineCapacity && enginePower == vehicle.enginePower && course == vehicle.course && damaged == vehicle.damaged && productionYear == vehicle.productionYear && Objects.equals(id, vehicle.id) && Objects.equals(mark, vehicle.mark) && Objects.equals(model, vehicle.model) && vehicleCondition == vehicle.vehicleCondition && Objects.equals(price, vehicle.price) && Objects.equals(audit, vehicle.audit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark, model, engineCapacity, enginePower, course, vehicleCondition, damaged, productionYear, price, audit);
    }
}


