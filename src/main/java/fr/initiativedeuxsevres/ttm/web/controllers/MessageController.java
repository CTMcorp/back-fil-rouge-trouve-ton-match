package fr.initiativedeuxsevres.ttm.web.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import fr.initiativedeuxsevres.ttm.domain.services.MongoService;
import fr.initiativedeuxsevres.ttm.web.dto.MessageDto;

@Controller
public class MessageController {

    private final MongoService mongoService;
    private final SimpMessagingTemplate template;

    public MessageController (MongoService mongoService, SimpMessagingTemplate template) {
            this.mongoService = mongoService;
            this.template = template;
    }

    // Ecoute les messages publiés sur l'url
//    @MessageMapping("/broadcast")
//    @SendTo("/topic/reply")
//    public String broadcastMessage(@Payload String message) {
//        return "You have received a message: " + message;
//    }

@MessageMapping("/requestMessages")
    public void openMessagePage(){
        var messages = mongoService.getMessagesForConversations("1", "2");
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

    @MessageMapping("/send")
    public void sendMessage(MessageDto message) throws Exception {
        mongoService.insertMessage(message.getUser1(), message.getUser2(), message.getContent());
    }



}
