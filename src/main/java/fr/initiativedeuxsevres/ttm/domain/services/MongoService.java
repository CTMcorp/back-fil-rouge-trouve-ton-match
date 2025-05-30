package fr.initiativedeuxsevres.ttm.domain.services;

import java.io.IOException;
import java.util.Arrays;

import org.bson.BsonValue;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.FullDocument;

import fr.initiativedeuxsevres.ttm.domain.models.User;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MongoService {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUrl;

    @Value("${spring.data.mongodb.database}")
    private String mongoDb;

    private MongoClient client;
    private MongoDatabase db;
    private MongoCollection<Document> messageCollection;

    @PostConstruct
    void init() throws IOException {
        log.info("start");
        client = MongoClients.create(mongoUrl);
        db = client.getDatabase(mongoDb);
        messageCollection = db.getCollection("messages");
    }

    public BsonValue insertMessage(User sender, User dest, String content) {
        return messageCollection.insertOne(
                new Document("sender", sender.getUserId())
                        .append("dest", dest.getUserId())
                        .append("content", content))
                .getInsertedId();
    }

    public MongoIterable<Document> getMessagesForConversations(User user1, User user2) {
        return messageCollection.aggregate(Arrays.asList(
                Aggregates.match(
                        Filters.and(
                                Filters.in("sender", user1.getUserId(), user2.getUserId()),
                                Filters.in("dest", user1.getUserId(), user2.getUserId())))))
                .map(element -> element);
    }

    public ChangeStreamIterable<Document> listenForNewMessages(String user1, String user2){
        return messageCollection
                .watch(Arrays.asList(
                        Aggregates.match(
                                Filters.and(
                                        Filters.in("fullDocument.sender", user1, user2),
                                        Filters.in("fullDocument.dest", user1, user2)
                                )
                        )
                )).fullDocument(FullDocument.UPDATE_LOOKUP);
    }

}
