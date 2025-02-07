package fr.initiativedeuxsevres.ttm.web.mapper;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.SecteursActivitesRepository;
import fr.initiativedeuxsevres.ttm.domain.repositories.TypesAccompagnementRepository;
import fr.initiativedeuxsevres.ttm.web.dto.RoleDto;
import fr.initiativedeuxsevres.ttm.web.dto.SecteursActivitesDto;
import fr.initiativedeuxsevres.ttm.web.dto.TypesAccompagnementDto;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapperDto {
    private final SecteursActivitesRepository secteursActivitesRepository;
    private final TypesAccompagnementRepository typesAccompagnementRepository;

    public UserMapperDto(SecteursActivitesRepository secteursActivitesRepository, TypesAccompagnementRepository typesAccompagnementRepository) {
        this.secteursActivitesRepository = secteursActivitesRepository;
        this.typesAccompagnementRepository = typesAccompagnementRepository;
    }

    public UserDto mapUserToUserDto(User user) {
        RoleDto role = RoleDto.mapRoleToRoleDto(user.role());

        List<SecteursActivitesDto> secteurs = user.secteursActivites().stream()
                .map((secteur) -> SecteursActivites.mapStringToName(secteur.name))
                .map(SecteursActivitesDto::mapSecteursActivitesToSecteursActivitesDto)
                .collect(Collectors.toList());

        List<TypesAccompagnementDto> types = typesAccompagnementRepository.findTypesByUserId(user.userId()).stream().map(
                TypesAccompagnementDto::mapTypesAccompagnementToTypesAccompagnementDto
        ).toList();

        return new UserDto(
                user.userId(),
                user.firstname(),
                user.lastname(),
                user.email(),
                user.description(),
                role,
                secteurs,
                types);
    }
}
