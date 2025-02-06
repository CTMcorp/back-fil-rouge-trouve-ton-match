package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.services.SecteursActivitesService;
import fr.initiativedeuxsevres.ttm.domain.services.TypesAccompagnementService;
import fr.initiativedeuxsevres.ttm.web.dto.SecteursActivitesDto;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.mapper.UserMapperDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/ttm")
public class SecteursTypesController {
    private final SecteursActivitesService secteursActivitesService;
    private final TypesAccompagnementService typesAccompagnementService;
    private final UserMapperDto userMapperDto;

    public SecteursTypesController(SecteursActivitesService secteursActivitesService, TypesAccompagnementService typesAccompagnementService, UserMapperDto userMapperDto) {
        this.secteursActivitesService = secteursActivitesService;
        this.typesAccompagnementService = typesAccompagnementService;
        this.userMapperDto = userMapperDto;
    }

    @PostMapping(value = "/me/secteurs/{secteurId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateSecteurs(@PathVariable int secteurId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }
        User user = (User) authentication.getPrincipal();
        System.out.println("Authenticated user: " + user);
        System.out.println("Secteur ID: " + secteurId);

        User updateUser = secteursActivitesService.addUserSecteur(user.userId(), secteurId);
        System.out.println("Updated user: " + updateUser);
        return userMapperDto.mapUserToUserDto(updateUser);
    }

    @GetMapping(value = "/me/secteurs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SecteursActivitesDto> findSecteursByUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        System.out.println("Authenticated user: " + user);
        if (!authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authenticated");
        }
        List<SecteursActivites> secteursList = secteursActivitesService.findSecteursByUserId(user.userId());
        return secteursList.stream().map(SecteursActivitesDto::mapSecteursActivitesToSecteursActivitesDto).collect(Collectors.toList());
    }

    // TODO : ne fonctionne pas sur la route /ttm/... car ne récupère pas le user authentifié.
    //  Mais sur une route genre /secteurs/all c'est ok si pas besoin d'être authentifié
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SecteursActivitesDto> allSecteurs(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        log.info(String.valueOf(user.userId()));
        if (!authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authenticated");
        }

        List<SecteursActivites> secteursList = secteursActivitesService.findAllSecteurs(user.userId());

        if (secteursList == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Secteurs list is null");
        }

        return secteursList.stream()
                .filter(Objects::nonNull) // Filtrer les éléments null
                .map(SecteursActivitesDto::mapSecteursActivitesToSecteursActivitesDto)
                .collect(Collectors.toList());
    }

    /*
    // FIXME : méthode de test pour voir si le user est bien récupéré et ce n'est pas le cas.
        Essaie dans le userController et ne fonctionne pas non plus
    @GetMapping("/test")
    public ResponseEntity<String> test(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok("Authenticated user: " + user.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }
    }*/

    @PostMapping(value = "/me/types/{typeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateTypes(@PathVariable int typeId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User updateUser = typesAccompagnementService.addUserType(user.userId(), typeId);
        return userMapperDto.mapUserToUserDto(updateUser);
    }
}
