package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.User;

public interface UserRepository {
    User register(String firstname, String lastname, String email, String password, String role);

    User logIn(String email);

    User getUserById(String id);
}
