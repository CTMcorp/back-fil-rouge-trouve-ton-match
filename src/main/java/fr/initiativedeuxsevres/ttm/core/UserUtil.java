package fr.initiativedeuxsevres.ttm.core;

import fr.initiativedeuxsevres.ttm.domain.models.Role;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserUtil {
    public static List<User> getUser(String query, Object[] params, JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.query(query, params, (rs, rowNum) -> {
                    Role role = Role.valueOf(rs.getString("role_name"));
                    return new User(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("description"),
                            role,
                            new ArrayList<>(),
                            new ArrayList<>()
                    );
                }
        );
    }
}