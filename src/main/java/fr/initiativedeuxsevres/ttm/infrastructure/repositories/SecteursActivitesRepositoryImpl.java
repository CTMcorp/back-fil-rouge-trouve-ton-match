package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.SecteursActivitesRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static fr.initiativedeuxsevres.ttm.infrastructure.repositories.TypesAccompagnementRepositoryImpl.getUser;

@Repository
public class SecteursActivitesRepositoryImpl implements SecteursActivitesRepository {
    private final JdbcTemplate jdbcTemplate;

    public SecteursActivitesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUserSecteur(UUID userId, int idNumber) {
        String insert = "INSERT INTO users_secteurs (users_id, secteurs_id_number) VALUES (?, ?)";
        jdbcTemplate.update(insert, userId, idNumber);

        String select = "SELECT users.*, secteurs.name FROM users JOIN users_secteurs us ON users.id = us.users_id JOIN secteurs ON secteurs.id_number = us.secteurs_id_number WHERE secteurs.id_number = ?";
        return getUser(idNumber, select, jdbcTemplate);
    }

    @Override
    public List<SecteursActivites> findSecteursByUserId(UUID userId) {
        String query = "SELECT secteurs.name FROM secteurs JOIN users_secteurs us ON secteurs.id_number = us.secteurs_id_number WHERE us.users_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId.toString()}, (rs, rowNum) -> {
            String secteurName = rs.getString("name");
            return Arrays.stream(SecteursActivites.values()).filter(enumVal -> enumVal.name.equals(secteurName)).findFirst().orElse(null);
        });
    }

    public List<SecteursActivites> findAllSecteurs() {
        String query = "SELECT name FROM secteurs";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            String secteurName = rs.getString("name");
            return Arrays.stream(SecteursActivites.values()).filter(enumVal -> enumVal.name.equals(secteurName)).findAny().orElse(null);
        });
    }
}

