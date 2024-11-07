package fr.initiativedeuxsevres.ttm.web.dto;

import fr.initiativedeuxsevres.ttm.domain.models.Roles;

import java.util.UUID;

public record LoginRequestDto(UUID userId, String firstname, String lastname, String email, String password, String role) {
}
