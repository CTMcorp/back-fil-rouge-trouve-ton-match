package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    User register(String firstname, String lastname, String email, String password, String role);

    User logIn(String email);

    Optional<User> findById(UUID userId);
}
