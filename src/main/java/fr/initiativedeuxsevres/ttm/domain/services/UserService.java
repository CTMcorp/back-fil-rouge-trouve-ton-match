package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.Roles;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String firstname, String lastname, String email, String password, Roles role);

    User loadUserByUsername(String email);
}
