package fr.initiativedeuxsevres.ttm.web.dto;

import fr.initiativedeuxsevres.ttm.domain.models.Role;

public enum RoleDto {
    ADMINISTRATEUR,
    PORTEUR,
    PARRAIN;

    public static RoleDto mapRoleToRoleDto(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("SecteursActivites cannot be null");
        }
        return switch (role) {
            case ADMINISTRATEUR -> RoleDto.ADMINISTRATEUR;
            case PORTEUR -> RoleDto.PORTEUR;
            case PARRAIN -> RoleDto.PARRAIN;
            default -> null;
        };
    }
}
