package pl.neverendingcode.security.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.neverendingcode.security.entity.Role;
import pl.neverendingcode.security.enums.UserRole;

import java.util.Optional;

@Repository
public interface RoleRepository extends RoleRepositoryImpl, JpaRepository<Role, Long> {
    Optional<Role> findByUserRole(UserRole userRole);
}
