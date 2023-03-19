package pl.neverendingcode.vehicle.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.vehicle.enums.VehicleCondition;

import java.math.BigDecimal;

@Data
@SuperBuilder
public class VehicleDTO {
    private Long id;
    private String mark;
    private String model;
    private int engineCapacity;
    private int enginePower;
    private int course;
    private VehicleCondition vehicleCondition;
    private boolean damaged;
    private int productionYear;
    private BigDecimal price;
}
