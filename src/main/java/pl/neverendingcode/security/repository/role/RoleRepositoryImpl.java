package pl.neverendingcode.security.repository.role;

import pl.neverendingcode.security.entity.Role;
import pl.neverendingcode.security.enums.UserRole;

import java.util.Optional;

public interface RoleRepositoryImpl {
    Optional<Role> findByUserRole(UserRole userRole);
}
