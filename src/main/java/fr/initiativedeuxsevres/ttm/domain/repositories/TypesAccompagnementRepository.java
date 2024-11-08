package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.TypesAccompagnement;

import java.util.List;
import java.util.UUID;

public interface TypesAccompagnementRepository {
    List<TypesAccompagnement> findTypesByUserId(UUID userId);
}
