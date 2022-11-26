package pl.neverendingcode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.neverendingcode.enums.VehicleCondition;

import javax.persistence.*;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_sequence")
    @SequenceGenerator(name = "vehicle_sequence", sequenceName = "vehicle_sequence", allocationSize = 1)
    private int id;
    @Column(name = "mark")
    protected String mark;
    @Column(name = "model")
    protected String model;
    @Column(name = "engine_capacity")
    protected int engineCapacity;
    @Column(name = "engine_power")
    protected int enginePower;
    @Column(name = "course")
    protected int course;
    @Column(name = "vehicle_condition")
    @Enumerated(EnumType.STRING)
    protected VehicleCondition vehicleCondition;
    @Column(name = "damaged")
    protected boolean damaged;
    @Column(name = "production_year")
    private int productionYear;
    @Column(name = "price")
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


