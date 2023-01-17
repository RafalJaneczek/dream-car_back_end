package pl.neverendingcode.security.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.neverendingcode.security.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends UserRepositoryImpl, JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
