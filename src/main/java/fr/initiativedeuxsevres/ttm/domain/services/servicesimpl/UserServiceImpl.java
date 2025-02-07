package fr.initiativedeuxsevres.ttm.domain.services.servicesimpl;

import fr.initiativedeuxsevres.ttm.config.JwtService;
import fr.initiativedeuxsevres.ttm.infrastructure.mappper.UserMapper;
import fr.initiativedeuxsevres.ttm.domain.models.Role;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.UserRepository;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import fr.initiativedeuxsevres.ttm.infrastructure.models.RoleInfra;
import fr.initiativedeuxsevres.ttm.web.dto.LoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager, @Lazy JwtService jwtService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @Override
    public User register(String firstname, String lastname, String email, String password, String roleName) {
        try {
            if (roleName == null || roleName.isEmpty()) {
                throw new IllegalArgumentException("Role cannot be null");
            }
            String encodedPassword = passwordEncoder.encode(password);
            RoleInfra roleInfra = userRepository.findRoleByName(roleName);
            return userRepository.register(firstname, lastname, email, encodedPassword, roleInfra);
        } catch (Exception e) {
            throw new RuntimeException("Registration failed", e);
        }
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.logIn(email);
    }

    @Override
    public String logIn(LoginDto loginDto) {
        User user = userRepository.logIn(loginDto.email());
        // authentifie le user avec email et password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.password()
                )
        );
        // définit l'auth dans le contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // génère token pour le user authentifié
        return jwtService.generateToken(user);
    }
}
