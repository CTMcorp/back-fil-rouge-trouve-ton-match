package fr.initiativedeuxsevres.ttm.web.dto;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MessageDto {
    private User user2;
    private String content;
}
