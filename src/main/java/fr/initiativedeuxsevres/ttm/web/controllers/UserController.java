package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import fr.initiativedeuxsevres.ttm.web.dto.JwtAuthResponse;
import fr.initiativedeuxsevres.ttm.web.dto.LoginRequestDto;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.mapper.UserMapperDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final UserMapperDto userMapper;
    /*private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;*/

    public UserController(UserService userService, UserMapperDto userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> logIn(@RequestBody LoginRequestDto loginRequestDto) {
        // authentifie le user et génère un token
        String token = userService.logIn(loginRequestDto);

        // crée une réponse contenant le token
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token, "Bearer");
        // return la réponse avec statut 200
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}


    /*public Void login(@RequestBody LoginRequestDto loginRequest, HttpServletRequest request, HttpServletResponse response) {
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
    }*/