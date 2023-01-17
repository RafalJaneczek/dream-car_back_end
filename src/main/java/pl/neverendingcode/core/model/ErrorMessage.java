package pl.neverendingcode.core.model;

import java.time.LocalDateTime;

public record ErrorMessage(int statusCode, LocalDateTime timestamp, String message) {
}
