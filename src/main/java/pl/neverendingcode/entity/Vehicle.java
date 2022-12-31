package pl.neverendingcode.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.enums.VehicleCondition;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Car.class, name = "car"),
        @JsonSubTypes.Type(value = Motorcycle.class, name = "motorcycle")
})
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_sequence")
    @SequenceGenerator(name = "vehicle_sequence", sequenceName = "vehicle_sequence", allocationSize = 1)
    private int id;

    @Column(name = "mark")
    @Min(message = "Mark cannot be shorter than 2 characters", value = 2)
    @NotEmpty(message = "Mark must not be empty")
    protected String mark;

    @Column(name = "model")
    @Min(message = "Model cannot be shorter than 2 characters", value = 2)
    @NotEmpty(message = "Model must not be empty")
    protected String model;

    @Column(name = "engine_capacity")
    @NotNull(message = "Engine capacity must not be empty")
    protected int engineCapacity;

    @Column(name = "engine_power")
    @NotNull(message = "Engine power must not be empty")
    protected int enginePower;

    @Column(name = "course")
    @NotNull(message = "Course must not be empty")
    protected int course;

    @Column(name = "vehicle_condition")
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Vehicle condition must not be empty")
    protected VehicleCondition vehicleCondition;

    @Column(name = "damaged")
    @NotNull(message = "Damaged param must not be empty")
    protected boolean damaged;

    @Column(name = "production_year")
    @NotNull(message = "Production year must not be empty")
    private int productionYear;

    @Column(name = "price")
    @NotNull(message = "Price must not be empty")
    private BigInteger price;

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

}


