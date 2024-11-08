package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.services.SecteursActivitesService;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import fr.initiativedeuxsevres.ttm.web.mapper.UserMapperDto;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class SecteursActivitesController {
    private final SecteursActivitesService secteursActivitesService;
    private final UserMapperDto userMapperDto;

    public SecteursActivitesController(SecteursActivitesService secteursActivitesService, UserMapperDto userMapperDto) {
        this.secteursActivitesService = secteursActivitesService;
        this.userMapperDto = userMapperDto;
    }

    @PostMapping(value = "/me/{secteurId}", produces = "application/json")
    public UserDto updateSecteurs(@PathVariable UUID secteurId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User updateUser = secteursActivitesService.addUserSecteur(user.userId(), secteurId);
        return userMapperDto.mapUserToUserDto(updateUser);
    }
}
