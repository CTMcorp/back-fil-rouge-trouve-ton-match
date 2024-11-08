package fr.initiativedeuxsevres.ttm.infrastructure.models;

import java.util.UUID;

public record UserInfra(UUID userId, String firstname, String lastname, String email, String password, String description, String role) {
}
