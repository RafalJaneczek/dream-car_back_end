package pl.neverendingcode.vehicle.car.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.core.entity.File;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "t_car_photos")
public class CarPhoto extends File {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarPhoto carPhoto)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(car, carPhoto.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), car);
    }
}
