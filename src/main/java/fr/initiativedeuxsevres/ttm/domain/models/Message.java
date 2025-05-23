package fr.initiativedeuxsevres.ttm.domain.models;

public record Message(
        int id,
        User user1,
        User user2,
        String content
) {
}
