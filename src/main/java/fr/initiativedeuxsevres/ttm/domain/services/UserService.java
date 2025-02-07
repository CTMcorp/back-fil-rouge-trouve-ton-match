package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.Role;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.infrastructure.models.RoleInfra;
import fr.initiativedeuxsevres.ttm.web.dto.LoginDto;
import fr.initiativedeuxsevres.ttm.web.dto.LoginRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(String firstname, String lastname, String email, String password, String roleName);

    User loadUserByUsername(String email);

    String logIn(LoginDto loginDto);
}
