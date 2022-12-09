package pl.neverendingcode.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class Audit {

    @Column(name = "created_on")
    @PastOrPresent(message = "Created on must be a date in the past or present")
    @NotNull(message = "Created on must not be empty")
    private LocalDateTime createdOn;
    @Column(name = "updated_on")
    @NotNull(message = "Updated on must not be empty")
    private LocalDateTime updatedOn;

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedOn = LocalDateTime.now();
    }

}
