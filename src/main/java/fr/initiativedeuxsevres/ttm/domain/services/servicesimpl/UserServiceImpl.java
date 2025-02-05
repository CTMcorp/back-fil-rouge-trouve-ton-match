package fr.initiativedeuxsevres.ttm.domain.services.servicesimpl;

import fr.initiativedeuxsevres.ttm.config.JwtTokenProvider;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.UserRepository;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import fr.initiativedeuxsevres.ttm.web.dto.LoginRequestDto;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.mapper.UserMapperDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapperDto userMapperDto;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserMapperDto userMapperDto) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userMapperDto = userMapperDto;
    }

    @Override
    public User register(String firstname, String lastname, String email, String password, String role) {
        try {
            if (role == null) {
                throw new IllegalArgumentException("Role cannot be null");
            }
            String encodedPassword = passwordEncoder.encode(password);
            return userRepository.register(firstname, lastname, email, encodedPassword, role);
        } catch (Exception e) {
            throw new RuntimeException("Registration failed", e);
        }

    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.logIn(email);
    }

    @Override
    public User getUserById (String id) throws UsernameNotFoundException {
        return userRepository.getUserById(id);
    }

    @Override
    public String logIn(LoginRequestDto loginRequestDto) {
        // authentifie le user avec email et password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.email(),
                        loginRequestDto.password()
                )
        );
        // définit l'auth dans le contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // génère token pour le user authentifié
        return jwtTokenProvider.generateToken(authentication, loginRequestDto.email());
    }
}
