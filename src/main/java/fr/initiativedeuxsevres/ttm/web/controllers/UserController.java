package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import fr.initiativedeuxsevres.ttm.web.dto.LoginRequestDto;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.mapper.UserMapperDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final UserMapperDto userMapper;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;

    public UserController(UserService userService, UserMapperDto userMapper, AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository, SecurityContextHolderStrategy securityContextHolderStrategy) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    @PostMapping(value = "/register", produces = "application/json")
    public UserDto register(@RequestBody LoginRequestDto loginRequest) {
        User user = userService.register(
                loginRequest.firstname(),
                loginRequest.lastname(),
                loginRequest.email(),
                loginRequest.password(),
                loginRequest.role()
                );
        return userMapper.mapUserToUserDto(user);
    }

    @PostMapping(value = "/login", produces = "application/json")
    public Void login(@RequestBody LoginRequestDto loginRequest, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequest.email(),
                loginRequest.password());

        Authentication authentication= authenticationManager.authenticate(token);

        if (authentication.isAuthenticated()) {
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextRepository.saveContext(context, request, response);
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
        return null;
    }
}
