package pl.neverendingcode.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.neverendingcode.security.entity.Role;
import pl.neverendingcode.security.entity.User;
import pl.neverendingcode.security.enums.UserRole;
import pl.neverendingcode.security.exception.RoleNotFoundException;
import pl.neverendingcode.security.exception.UserEmailException;
import pl.neverendingcode.security.exception.UsernameException;
import pl.neverendingcode.security.jwt.JwtUtils;
import pl.neverendingcode.security.model.request.LoginRequest;
import pl.neverendingcode.security.model.request.SignupRequest;
import pl.neverendingcode.security.model.response.JwtResponse;
import pl.neverendingcode.security.repository.role.RoleRepositoryImpl;
import pl.neverendingcode.security.repository.user.UserRepositoryImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepositoryImpl userRepository;
    private final RoleRepositoryImpl roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseEntity<JwtResponse> loginUser(LoginRequest request) {
        log.info("Start authenticating process for user: {}", request.username());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        JwtResponse response = new JwtResponse(jwt,userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);

        log.info("Finish authenticating process. Response: {}", response);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> signupUser(SignupRequest request) {
        log.info("Start registering user process, request {}: ", request);

        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameException("Username is already taken!");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new UserEmailException("Email is already in use!");
        }

        User user = new User(request.username(), request.email(), passwordEncoder.encode(request.password()), prepareUserRoles(Set.of(UserRole.ROLE_USER)));
        userRepository.save(user);

        log.info("Finish registering user process");
        return ResponseEntity.noContent().build();
    }

    private Role findByUserRole(UserRole userRole) {
        return roleRepository.findByUserRole(userRole)
                .orElseThrow(() -> new RoleNotFoundException("Role is not found."));
    }

    private Set<Role> prepareUserRoles(Set<UserRole> selectedRoles) {
        Set<Role> roles = new HashSet<>();

        if (selectedRoles == null) {
            Role userRole = findByUserRole(UserRole.ROLE_USER);
            roles.add(userRole);
        } else {
            selectedRoles.forEach(role -> {
                switch (role.toString()) {
                    case "ROLE_ADMIN" -> {
                        Role adminRole = findByUserRole(UserRole.ROLE_ADMIN);
                        roles.add(adminRole);
                    }
                    case "ROLE_MOD" -> {
                        Role adminRole = findByUserRole(UserRole.ROLE_MOD);
                        roles.add(adminRole);
                    }
                    default -> {
                        Role userRole = findByUserRole(UserRole.ROLE_USER);
                        roles.add(userRole);
                    }
                }
            });
        }
        return roles;
    }

}
