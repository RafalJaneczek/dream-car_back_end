package pl.neverendingcode.vehicle.car.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.core.model.AuditResponse;
import pl.neverendingcode.vehicle.car.entity.CarPhoto;
import pl.neverendingcode.vehicle.car.enums.BodyType;
import pl.neverendingcode.vehicle.car.enums.EngineType;
import pl.neverendingcode.vehicle.model.dto.VehicleDTO;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CarDTO extends VehicleDTO {
    private EngineType engineType;
    private BodyType bodyType;
    private int numberOfSeats;
    private AuditResponse audit;
    private List<CarPhoto> carPhotos;
}
