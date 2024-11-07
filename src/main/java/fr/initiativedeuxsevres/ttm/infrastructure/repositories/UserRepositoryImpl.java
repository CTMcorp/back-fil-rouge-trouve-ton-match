package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.Roles;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User register(String firstname, String lastname, String email, String password, Roles role) {
        jdbcTemplate.update(
                "INSERT INTO users(firstname, lastname, email, password, roles_name) VALUES (?, ?, ?, ?, ?)",
                firstname, lastname, email, password, role.name()
        );
        return logIn(email);
    }

    @Override
    public User logIn(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT users.*, roles.name AS roles_name FROM users JOIN roles ON roles.name = users.roles_name WHERE email = ?",
                (rs, rowNum) -> {
                    Roles role = new Roles(rs.getString("roles_name"));
                    return new User(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("description"),
                            role
                            );
                }, email);
    }
}
