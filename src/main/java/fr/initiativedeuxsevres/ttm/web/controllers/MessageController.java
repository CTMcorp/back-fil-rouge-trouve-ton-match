package fr.initiativedeuxsevres.ttm.web.controllers;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import fr.initiativedeuxsevres.ttm.domain.services.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import fr.initiativedeuxsevres.ttm.domain.services.MongoService;
import fr.initiativedeuxsevres.ttm.web.dto.MessageDto;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MongoService mongoService;
    private final SimpMessagingTemplate template;
    private final UserService userService;

@MessageMapping("/requestMessages/{conversationId}")
    public void openMessagePage(Authentication auth, @PathParam("conversationId") String conversationId){

    if (auth == null) {
        throw new RuntimeException("Authentication is null");
    }

    User user = (User) auth.getPrincipal();

    User conversation = userService.findById(UUID.fromString(conversationId)).orElseThrow(() -> new RuntimeException(("Conversation not found")));

    var messages = mongoService.getMessagesForConversations(user, conversation);
        template.convertAndSend("/getMessages", messages);

    mongoService.listenForNewMessages("1","2")
            .forEach(doc -> {
                switch (doc.getOperationType()){
                    case INSERT:
                        template.convertAndSend("/newMessage", doc.getFullDocument().toJson());
                        break;
                    case DELETE:
                        template.convertAndSend("/deleteMessage", doc.getDocumentKey().get("_id").asObjectId().getValue());
                        break;
                    case UPDATE:
                        template.convertAndSend("/updateMessage", doc.getUpdateDescription().getUpdatedFields().toJson());
                        break;
                    default:
                        new Exception("Not yet implemented OperationType: {}" + doc.getOperationType());
                }
            });
    }

    @MessageMapping("/send/{conversationId}")
    public void sendMessage(String message, Authentication auth, @PathParam("conversationId") UUID conversationId) throws Exception {
        User user = (User) auth.getPrincipal();

        User conversation = userService.findById(conversationId).orElseThrow(() -> new RuntimeException(("Conversation not found")));

        mongoService.insertMessage(user, conversation, message);
    }
}
