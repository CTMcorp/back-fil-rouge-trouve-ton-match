package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface SecteursActivitesRepository {

    User addUserSecteur(UUID userId, UUID secteurId);

    List<SecteursActivites> findSecteursByUserId(UUID userId);
}
