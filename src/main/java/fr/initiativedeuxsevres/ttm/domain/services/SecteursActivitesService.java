package fr.initiativedeuxsevres.ttm.domain.services;

import fr.initiativedeuxsevres.ttm.domain.models.SecteursActivites;
import fr.initiativedeuxsevres.ttm.domain.models.User;

import java.util.List;
import java.util.UUID;

public interface SecteursActivitesService {

    User addUserSecteur(UUID userId, int idNumber);

    List<SecteursActivites> findSecteursByUserId(UUID userId);

    List<SecteursActivites> findAllSecteurs();
}
