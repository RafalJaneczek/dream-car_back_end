package pl.neverendingcode.security.model.request;

import javax.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String username, @NotBlank String password) {
}
