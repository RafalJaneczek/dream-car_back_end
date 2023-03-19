package pl.neverendingcode.vehicle.motorcycle.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.core.model.AuditResponse;
import pl.neverendingcode.vehicle.model.dto.VehicleDTO;
import pl.neverendingcode.vehicle.motorcycle.entity.MotorcyclePhoto;
import pl.neverendingcode.vehicle.motorcycle.enums.EngineType;
import pl.neverendingcode.vehicle.motorcycle.enums.Type;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MotorcycleDTO extends VehicleDTO {
    private EngineType engineType;
    private Type type;
    private AuditResponse audit;
    private List<MotorcyclePhoto> motorcyclePhotos;
}
