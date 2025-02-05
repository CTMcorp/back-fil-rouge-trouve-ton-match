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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final UserMapperDto userMapper;

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

    @PostMapping(value = "/login")
    public ResponseEntity<JwtAuthResponse> logIn(@RequestBody LoginRequestDto loginRequestDto) {
        // authentifie le user et génère un token
        String token = userService.logIn(loginRequestDto);

        // crée une réponse contenant le token
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse(token);
        // return la réponse avec statut 200
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE) // réponse format JSON
    public ResponseEntity<User> getUserProfile(Authentication authentication) {
        // Récupère l'ID utilisateur à partir de l'authentification
        String id = authentication.getName();
        // Charge les détails de l'utilisateur
        User user = userService.getUserById(id);
        System.out.println(id);
        // Mappe l'utilisateur vers un DTO
        //UserDto userDto = userMapper.mapUserToUserDto(user);
        // Retourne les informations de l'utilisateur
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}