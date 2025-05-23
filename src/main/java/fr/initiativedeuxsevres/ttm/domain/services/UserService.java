package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.web.dto.LoginRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    User register(String firstname, String lastname, String email, String password, String role);

    User loadUserByUsername(String email);

    String logIn(LoginRequestDto loginRequestDto);

    Optional<User> findById(UUID userId);
}
