package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

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

        String select = "SELECT users.*, types.name FROM users JOIN users_types us ON users.id = us.users_id JOIN types ON types.id_number = us.types_id_number WHERE types.id_number = ?";
        return getUser(typeId, select, jdbcTemplate);
    }

    static User getUser(int typeId, String select, JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForObject(select,
                new Object[]{typeId}, (rs, rowNum) ->
                        new User(
                                UUID.fromString(rs.getString("id")),
                                rs.getString("firstname"),
                                rs.getString("lastname"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("description"),
                                rs.getString("role"),
                                new ArrayList<>(),
                                new ArrayList<>()
                        ));
    }

    @Override
    public List<TypesAccompagnement> findTypesByUserId(UUID userId) {
        String query = "SELECT types.name FROM types JOIN users_types ut ON types.id_number = ut.types_id_number WHERE ut.users_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId.toString()}, (rs, rowNum) -> {
            String typeName = rs.getString("name").toUpperCase().replace(" ", "_");
            return TypesAccompagnement.valueOf(typeName);
        });
    }
}
