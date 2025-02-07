package fr.initiativedeuxsevres.ttm.infrastructure.mappper;

import fr.initiativedeuxsevres.ttm.domain.models.Role;
import fr.initiativedeuxsevres.ttm.infrastructure.models.RoleInfra;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public Role roleInfraToRole(RoleInfra roleInfra) {
        return Role.mapStringToName(roleInfra.name());
    }
}
