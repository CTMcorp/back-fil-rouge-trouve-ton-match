package fr.initiativedeuxsevres.ttm.web.dto;

public record LoginRequestDto(String firstname, String lastname, String email, String password, RoleDto role) {
}
