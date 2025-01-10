package fr.initiativedeuxsevres.ttm.web.dto;

import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID userId,
        String firstname,
        String lastname,
        String email,
        String description,
        String role,
        List<SecteursActivitesDto> secteursActivites,
        List<TypesAccompagnementDto> typesAccompagnements
) {}
