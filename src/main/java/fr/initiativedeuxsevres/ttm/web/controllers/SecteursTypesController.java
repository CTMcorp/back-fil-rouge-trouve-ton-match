package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.services.SecteursActivitesService;
import fr.initiativedeuxsevres.ttm.domain.services.TypesAccompagnementService;
import fr.initiativedeuxsevres.ttm.web.dto.SecteursActivitesDto;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.mapper.UserMapperDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping(value = "/me/allsecteurs", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SecteursActivitesDto> allSecteurs(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (!authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not authenticated");
        }
        List<SecteursActivites> secteursList = secteursActivitesService.findAllSecteurs();
        return secteursList.stream().map(SecteursActivitesDto::mapSecteursActivitesToSecteursActivitesDto).collect(Collectors.toList());
    }

    @PostMapping(value = "/me/types/{typeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateTypes(@PathVariable int typeId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User updateUser = typesAccompagnementService.addUserType(user.userId(), typeId);
        return userMapperDto.mapUserToUserDto(updateUser);
    }
}
