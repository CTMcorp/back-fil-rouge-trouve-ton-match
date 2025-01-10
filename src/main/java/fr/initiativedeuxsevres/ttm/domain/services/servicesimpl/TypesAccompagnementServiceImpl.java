package fr.initiativedeuxsevres.ttm.domain.services.servicesimpl;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.repositories.TypesAccompagnementRepository;
import fr.initiativedeuxsevres.ttm.domain.services.TypesAccompagnementService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TypesAccompagnementServiceImpl implements TypesAccompagnementService {
    private final TypesAccompagnementRepository typesAccompagnementRepository;

    public TypesAccompagnementServiceImpl(TypesAccompagnementRepository typesAccompagnementRepository) {
        this.typesAccompagnementRepository = typesAccompagnementRepository;
    }

    @Override
    public User addUserType(UUID userId, int typeId) {
        return typesAccompagnementRepository.addUserType(userId, typeId);
    }
}
