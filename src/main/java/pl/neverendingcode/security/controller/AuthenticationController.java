package pl.neverendingcode.security.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.neverendingcode.security.model.request.LoginRequest;
import pl.neverendingcode.security.model.request.SignupRequest;
import pl.neverendingcode.security.model.response.JwtResponse;
import pl.neverendingcode.security.service.AuthenticationServiceImpl;

@RestController
@RequestMapping("/api/auth/user")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest request) {
        return authenticationService.signupUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody LoginRequest request) {
        return authenticationService.loginUser(request);
    }

}
