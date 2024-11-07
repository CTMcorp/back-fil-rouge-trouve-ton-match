package fr.initiativedeuxsevres.ttm.domain.models;

public record Roles(String name) {
}
/*public enum Roles {
    ADMIN,
    PORTEUR,
    PARRAIN;

    public static Roles mapStringToRoles(String name) {
        return Arrays.stream(Roles.values())
                .filter((role) -> role.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}*/
