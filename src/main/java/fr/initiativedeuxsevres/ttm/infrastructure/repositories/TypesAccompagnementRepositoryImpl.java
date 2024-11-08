package fr.initiativedeuxsevres.ttm.infrastructure.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;
import fr.initiativedeuxsevres.ttm.domain.repositories.TypesAccompagnementRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TypesAccompagnementRepositoryImpl implements TypesAccompagnementRepository {
    private final JdbcTemplate jdbcTemplate;

    public TypesAccompagnementRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TypesAccompagnement> findTypesByUserId(UUID userId) {
        String query = "SELECT types.name FROM types JOIN users_types ut ON types.id = ut.types_id WHERE ut.users_id = ?";
        return jdbcTemplate.query(query, new Object[]{userId.toString()}, (rs, rowNum) -> {
            String typeName = rs.getString("name").toUpperCase().replace(" ", "_");
            return TypesAccompagnement.valueOf(typeName);
        });
    }
}
