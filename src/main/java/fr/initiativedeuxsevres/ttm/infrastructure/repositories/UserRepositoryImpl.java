package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

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
    public User register(String firstname, String lastname, String email, String password, String role) {
        jdbcTemplate.update(
                "INSERT INTO users(firstname, lastname, email, password, role) VALUES (?, ?, ?, ?, ?)",
                firstname, lastname, email, password, role
        );
        return logIn(email);
    }

    @Override
    public User logIn(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT users.* FROM users WHERE email = ?",
                (rs, rowNum) -> {
                    return new User(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("description"),
                            rs.getString("role")
                            );
                }, email);
    }
}
