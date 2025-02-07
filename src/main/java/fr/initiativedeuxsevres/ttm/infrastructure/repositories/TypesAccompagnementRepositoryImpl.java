package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.core.UserUtil;
import fr.initiativedeuxsevres.ttm.domain.models.Role;
import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.TypesAccompagnementRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TypesAccompagnementRepositoryImpl implements TypesAccompagnementRepository {
    private final JdbcTemplate jdbcTemplate;

    public TypesAccompagnementRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUserType(UUID userId, int typeId) {
        String insert = "INSERT INTO users_types (users_id, types_id_number) VALUES (?, ?)";
        jdbcTemplate.update(insert, userId, typeId);

        String select = "SELECT u.*, r.id AS role_id, r.name AS role_name, t.name AS type_name FROM users u" +
                "JOIN users_types ut ON u.id = ut.users_id" +
                "OIN types t ON t.id_number = ut.types_id_number" +
                "LEFT JOIN users_roles ur ON u.id = ur.users_id" +
                "LEFT JOIN roles r ON ur.roles_id = r.id" +
                "WHERE t.id_number = ?";
        List<User> users = UserUtil.getUser(select, new Object[]{typeId}, jdbcTemplate);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<TypesAccompagnement> findTypesByUserId(UUID userId) {
        String query = "SELECT t.name FROM types t JOIN users_types ut ON t.id_number = ut.types_id_number WHERE ut.users_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId.toString()}, (rs, rowNum) -> {
            String typeName = rs.getString("name").toUpperCase().replace(" ", "_");
            return TypesAccompagnement.valueOf(typeName);
        });
    }
}
