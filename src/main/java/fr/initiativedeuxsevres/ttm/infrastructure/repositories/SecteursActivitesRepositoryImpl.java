package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.SecteursActivitesRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class SecteursActivitesRepositoryImpl implements SecteursActivitesRepository {
    private final JdbcTemplate jdbcTemplate;

    public SecteursActivitesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUserSecteur(UUID userId, UUID secteurId) {
        String insert = "INSERT INTO users_secteurs (users_id, secteurs_id) VALUES (?, ?)";
        jdbcTemplate.update(insert, userId, secteurId);

        String select = "SELECT users.*, secteurs.name FROM users JOIN users_secteurs us ON users.id = us.users_id JOIN secteurs ON secteurs.id = us.secteurs_id WHERE secteurs.id = ?";
        User user = jdbcTemplate.queryForObject(select,
                new Object[]{secteurId.toString()}, (rs, rowNum) ->
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
        return user;
    }

    @Override
    public List<SecteursActivites> findSecteursByUserId(UUID userId) {
        String query = "SELECT secteurs.name FROM secteurs JOIN users_secteurs us ON secteurs.id = us.secteurs_id WHERE us.users_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId.toString()}, (rs, rowNum) -> {
            String secteurName = rs.getString("name");
            return Arrays.stream(SecteursActivites.values()).filter(enumVal -> enumVal.name.equals(secteurName)).findFirst().orElse(null);
        });
    }
}
