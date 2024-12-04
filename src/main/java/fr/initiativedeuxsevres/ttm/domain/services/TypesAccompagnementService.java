package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.util.UUID;

public interface TypesAccompagnementService {

    User addUserType(UUID userId, UUID typeId);
}
