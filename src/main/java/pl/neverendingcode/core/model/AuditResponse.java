package pl.neverendingcode.core.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode
public class AuditResponse {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
