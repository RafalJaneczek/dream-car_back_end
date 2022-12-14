package pl.neverendingcode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.neverendingcode.enums.motorcycle.Type;
import pl.neverendingcode.enums.motorcycle.EngineType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
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
