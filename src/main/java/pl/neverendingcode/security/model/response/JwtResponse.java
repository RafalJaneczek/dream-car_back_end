package pl.neverendingcode.security.model.response;

import java.util.List;

public record JwtResponse(String token, String type, Integer id, String username, String email,
                          List<String> roles) {
    public JwtResponse(String token, Integer id, String username, String email, List<String> roles) {
        this(token, "Bearer", id, username, email, roles);
    }
}
