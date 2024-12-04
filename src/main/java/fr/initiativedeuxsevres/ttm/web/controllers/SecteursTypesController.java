package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.services.SecteursActivitesService;
import fr.initiativedeuxsevres.ttm.domain.services.TypesAccompagnementService;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.mapper.UserMapperDto;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
    public UserDto updateSecteurs(@PathVariable UUID secteurId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User updateUser = secteursActivitesService.addUserSecteur(user.userId(), secteurId);
        return userMapperDto.mapUserToUserDto(updateUser);
    }

    @PostMapping(value = "/me/types/{typeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateTypes(@PathVariable UUID typeId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User updateUser = typesAccompagnementService.addUserType(user.userId(), typeId);
        return userMapperDto.mapUserToUserDto(updateUser);
    }
}
