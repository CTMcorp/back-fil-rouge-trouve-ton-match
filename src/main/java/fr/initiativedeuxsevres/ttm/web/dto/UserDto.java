package fr.initiativedeuxsevres.ttm.web.dto;

import java.util.UUID;

public record UserDto(UUID userId, String firstname, String lastname, String email, String password, String description, String role) {
}
