package fr.initiativedeuxsevres.ttm.domain.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public record User(
        UUID userId,
        String firstname,
        String lastname,
        String email,
        String password,
        String description,
        Role role,
        List<SecteursActivites> secteursActivites,
        List<TypesAccompagnement> typesAccompagnements) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            throw new RuntimeException("Role is null");
        }
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return firstname + lastname;
    }

    @Override
    public Role role() {
        return role;
    }
}
