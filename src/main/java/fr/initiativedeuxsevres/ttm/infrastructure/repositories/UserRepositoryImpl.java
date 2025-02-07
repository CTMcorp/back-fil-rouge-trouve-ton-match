package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.core.UserUtil;
import fr.initiativedeuxsevres.ttm.domain.models.Role;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.UserRepository;
import fr.initiativedeuxsevres.ttm.infrastructure.mappper.UserMapper;
import fr.initiativedeuxsevres.ttm.infrastructure.models.RoleInfra;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userMapper = userMapper;
    }

    public RoleInfra findRoleByName(String roleName) {
        String query = "SELECT id, name FROM roles WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{roleName}, (rs, rowNum) -> {
                UUID roleId = UUID.fromString(rs.getString("id"));
                String name = rs.getString("name");
                log.info("Role found: {}, {}", roleId, name);
                return new RoleInfra(roleId, name);
            });
        } catch (Exception e) {
            throw new RuntimeException("Role not found", e);
        }
    }

    //TODO : faire que register return un Void
    @Override
    public User register(String firstname, String lastname, String email, String password, RoleInfra roleInfra) {
        UUID userId = UUID.randomUUID();
        jdbcTemplate.update(
                "INSERT INTO users(id, firstname, lastname, email, password) VALUES (?, ?, ?, ?, ?)",
                userId, firstname, lastname, email, password
        );
        jdbcTemplate.update(
                "INSERT INTO users_roles(users_id, roles_id) VALUES (?, ?)",
                userId, roleInfra.roleId()
        );
        return logIn(email);
    }

    public User logIn(String email) {
        String query = "SELECT u.*, r.id AS role_id, r.name AS role_name FROM users u " +
                "LEFT JOIN users_roles ur ON u.id = ur.users_id " +
                "LEFT JOIN roles r ON ur.roles_id = r.id " +
                "WHERE u.email = ?";

        List<User> users = UserUtil.getUser(query, new Object[]{email}, jdbcTemplate);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
}
