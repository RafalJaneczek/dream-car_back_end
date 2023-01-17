package pl.neverendingcode.security.service;

import org.springframework.http.ResponseEntity;
import pl.neverendingcode.security.model.request.LoginRequest;
import pl.neverendingcode.security.model.request.SignupRequest;

public interface AuthenticationService {

    ResponseEntity<?> loginUser(LoginRequest request);

    ResponseEntity<?> signupUser(SignupRequest request);

}
