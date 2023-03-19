package pl.neverendingcode.vehicle.motorcycle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.core.entity.File;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "t_motorcycle_photos")
public class MotorcyclePhoto extends File {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorcycle_id", referencedColumnName = "id")
    private Motorcycle motorcycle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MotorcyclePhoto that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(motorcycle, that.motorcycle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), motorcycle);
    }
}
