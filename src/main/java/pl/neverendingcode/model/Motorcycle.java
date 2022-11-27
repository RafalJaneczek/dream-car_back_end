package pl.neverendingcode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.neverendingcode.enums.motorcycle.BodyType;
import pl.neverendingcode.enums.motorcycle.EngineType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "motorcycles")
public class Motorcycle extends Vehicle {
    @Column(name = "engine_type")
    private EngineType engineType;
    @Column(name = "body_type")
    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Embedded
    private Audit audit = new Audit();

    public void updateFrom(final Motorcycle source) {
        super.updateFrom(source);
        this.engineType = source.engineType;
        this.bodyType = source.bodyType;
    }
}
