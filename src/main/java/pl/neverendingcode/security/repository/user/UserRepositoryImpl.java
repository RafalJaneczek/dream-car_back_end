package pl.neverendingcode.security.repository.user;

import pl.neverendingcode.security.entity.User;

import java.util.Optional;

public interface UserRepositoryImpl {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User save(User user);

    void deleteById(int id);
}
