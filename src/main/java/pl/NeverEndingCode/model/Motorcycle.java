package pl.NeverEndingCode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.NeverEndingCode.enums.motorcycle.BodyType;
import pl.NeverEndingCode.enums.motorcycle.EngineType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MOTORTYCLES")
public class Motorcycle extends Vehicle {
    @Column(name = "ENGINE_TYPE")
    private EngineType engineType;
    @Column(name = "TYPE")
    private BodyType type;

    public void updateFrom(final Motorcycle source) {
        super.updateFrom(source);
        this.engineType = source.engineType;
        this.type = source.type;
    }
}
