package fr.initiativedeuxsevres.ttm.web.mapper;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.web.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapperDto {

    public UserDto mapUserToUserDto(User user) {
        return new UserDto(
                user.userId(),
                user.firstname(),
                user.lastname(),
                user.email(),
                user.password(),
                user.description(),
                user.role());
    }
}
