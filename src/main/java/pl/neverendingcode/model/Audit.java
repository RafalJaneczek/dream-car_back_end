package pl.neverendingcode.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Embeddable
public class Audit {

    @Column(name = "created_on")
    private String createdOn;
    @Column(name = "updated_on")
    private String updatedOn;

    @PrePersist
    public void prePersist() {
        createdOn = getFormattedDateTime();
    }

    @PreUpdate
    public void preUpdate() {
        updatedOn = getFormattedDateTime();
    }

    private String getFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

}
