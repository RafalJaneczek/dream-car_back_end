package pl.NeverEndingCode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.NeverEndingCode.enums.VehicleCondition;

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
    @Column(name = "MARK")
    protected String mark;
    @Column(name = "MODEL")
    protected String model;
    @Column(name = "ENGINE_CAPACITY")
    protected int engineCapacity;
    @Column(name = "ENGINE_POWER")
    protected int enginePower;
    @Column(name = "COURSE")
    protected int course;
    @Column(name = "VEHICLE_CONDITION")
    protected VehicleCondition vehicleCondition;
    @Column(name = "DAMAGED")
    protected boolean damaged;
    @Column(name = "PRODUCTION_YEAR")
    private int productionYear;
    @Column(name = "PRICE")
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


