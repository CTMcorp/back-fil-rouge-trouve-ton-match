package fr.initiativedeuxsevres.ttm.domain.models;

import java.util.Arrays;

public enum Role {
    ADMINISTRATEUR("Administrateur"),
    PORTEUR("Porteur"),
    PARRAIN("Parrain");

    public final String name;

    Role(String name) {
        this.name = name;
    }

    public static Role mapStringToName(String value) {
        return Arrays.stream(Role.values()).filter((element) ->
                element.name.equalsIgnoreCase(value)).findFirst().orElse(null);
    }
}
