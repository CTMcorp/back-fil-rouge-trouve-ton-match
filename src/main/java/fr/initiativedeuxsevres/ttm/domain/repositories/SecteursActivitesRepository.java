package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface SecteursActivitesRepository {

    User addUserSecteur(UUID userId, int idNumber);

    List<SecteursActivites> findSecteursByUserId(UUID userId);

    List<SecteursActivites> findAllSecteurs();
}
