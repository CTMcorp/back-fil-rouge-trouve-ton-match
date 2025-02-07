package fr.initiativedeuxsevres.ttm.domain.repositories;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.infrastructure.models.RoleInfra;

public interface UserRepository {
    RoleInfra findRoleByName(String roleName);

    User register(String firstname, String lastname, String email, String password, RoleInfra role);

    User logIn(String email);
}
