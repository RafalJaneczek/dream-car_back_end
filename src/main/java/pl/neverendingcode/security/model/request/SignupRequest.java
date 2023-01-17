package pl.neverendingcode.security.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record SignupRequest(
        @NotBlank @Size(min = 3, max = 20) String username,
        @NotBlank @Size(max = 50) String email,
        @NotBlank @Size(min = 6, max = 40) String password) {
}
