package fr.initiativedeuxsevres.ttm.domain.services.servicesimpl;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.UserRepository;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String firstname, String lastname, String email, String password, String role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        String encodedPassword = passwordEncoder.encode(password);
        return userRepository.register(firstname, lastname, email, encodedPassword, role);
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.logIn(email);
    }
}
