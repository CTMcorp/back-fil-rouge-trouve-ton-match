package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository {
    User register(String firstname, String lastname, String email, String password, String role);

    User logIn(String email);
}
