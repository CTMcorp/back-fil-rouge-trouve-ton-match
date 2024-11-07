package fr.initiativedeuxsevres.ttm.web.dto;

public record RolesDto(String name) {
}

/*public enum RolesDto {
    ADMIN,
    PORTEUR,
    PARRAIN;

    public static RolesDto mapRolesToRolesDto(Roles roles) {
        return switch (roles) {
            case ADMIN -> RolesDto.ADMIN;
            case PORTEUR -> RolesDto.PORTEUR;
            case PARRAIN -> RolesDto.PARRAIN;
            default -> null;
        };
    }
}*/
