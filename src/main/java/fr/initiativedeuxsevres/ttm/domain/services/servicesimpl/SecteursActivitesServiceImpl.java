package fr.initiativedeuxsevres.ttm.domain.services.servicesimpl;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.SecteursActivitesRepository;
import fr.initiativedeuxsevres.ttm.domain.services.SecteursActivitesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class SecteursActivitesServiceImpl implements SecteursActivitesService {
    private final SecteursActivitesRepository secteursActivitesRepository;

    public SecteursActivitesServiceImpl(SecteursActivitesRepository secteursActivitesRepository) {
        this.secteursActivitesRepository = secteursActivitesRepository;
    }

    @Override
    public User addUserSecteur(UUID userId, UUID secteurId) {
        return secteursActivitesRepository.addUserSecteur(userId, secteurId);
    }
}
