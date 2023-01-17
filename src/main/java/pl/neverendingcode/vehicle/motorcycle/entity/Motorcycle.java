package pl.neverendingcode.vehicle.motorcycle.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.vehicle.entity.Audit;
import pl.neverendingcode.vehicle.entity.Vehicle;
import pl.neverendingcode.vehicle.motorcycle.enums.EngineType;
import pl.neverendingcode.vehicle.motorcycle.enums.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "motorcycles")
public class Motorcycle extends Vehicle {

    @Column(name = "engine_type")
    @NotBlank(message = "Engine type must not be empty")
    private EngineType engineType;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Type must not be empty")
    private Type type;

    @Embedded
    private Audit audit = new Audit();

    public void updateFrom(final Motorcycle source) {
        super.updateFrom(source);
        this.engineType = source.engineType;
        this.type = source.type;
    }
}
