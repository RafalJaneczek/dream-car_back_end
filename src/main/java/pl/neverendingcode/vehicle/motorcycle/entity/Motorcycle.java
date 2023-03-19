package pl.neverendingcode.vehicle.motorcycle.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.vehicle.entity.Vehicle;
import pl.neverendingcode.vehicle.motorcycle.enums.EngineType;
import pl.neverendingcode.vehicle.motorcycle.enums.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_motorcycles")
public class Motorcycle extends Vehicle {

    @Column(name = "engine_type")
    @NotBlank(message = "Engine type must not be empty")
    private EngineType engineType;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Type must not be empty")
    private Type type;

    @OneToMany(mappedBy = "motorcycle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MotorcyclePhoto> motorcyclePhotos = new ArrayList<>();

    public void addPhoto(MotorcyclePhoto motorcyclePhoto) {
        motorcyclePhoto.setMotorcycle(this);
        this.motorcyclePhotos.add(motorcyclePhoto);
    }

    public void updateFrom(final Vehicle source, final List<MotorcyclePhoto> motorcyclePhotos) {
        super.updateFrom(source);
        if (source instanceof final Motorcycle motorcycle) {
            this.engineType = motorcycle.engineType;
            this.type = motorcycle.type;
            motorcyclePhotos.forEach(this::addPhoto);
        }
    }
}
