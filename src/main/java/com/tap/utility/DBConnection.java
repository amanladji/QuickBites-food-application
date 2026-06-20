package com.tap.utility;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;

public class DBConnection {
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static java.util.concurrent.ScheduledExecutorService scheduler;

    private static final String DEFAULT_URI = "mongodb+srv://amanahmed12347_db_user:wN3EuooInYexqQfX@cluster0.opqmhtw.mongodb.net/quickbite?retryWrites=true&w=majority&appName=Cluster0&maxIdleTimeMS=300000&connectTimeoutMS=10000&socketTimeoutMS=30000";
    private static final String DB_NAME = "quickbite";

    public static synchronized MongoDatabase getDatabase() {
        if (database == null) {
            String uri = System.getenv("MONGO_URI");
            if (uri == null || uri.isEmpty()) {
                uri = DEFAULT_URI;
            }
            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase(DB_NAME);
            startKeepAlive();
        }
        return database;
    }

    private static void startKeepAlive() {
        scheduler = java.util.concurrent.Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "mongo-keepalive");
            t.setDaemon(true);
            return t;
        });
        scheduler.scheduleAtFixedRate(() -> {
            try {
                database.runCommand(new Document("ping", 1));
            } catch (Exception ignored) {
            }
        }, 4, 4, java.util.concurrent.TimeUnit.MINUTES);
    }

    public static MongoCollection<Document> getCollection(String name) {
        return getDatabase().getCollection(name);
    }

    public static int getNextSequence(String collectionName) {
        MongoCollection<Document> counters = getCollection("counters");
        Document filter = new Document("_id", collectionName);
        Document update = new Document("$inc", new Document("seq", 1));
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions()
                .upsert(true)
                .returnDocument(ReturnDocument.AFTER);
        Document result = counters.findOneAndUpdate(filter, update, options);
        if (result == null) {
            throw new RuntimeException("Failed to get next sequence for " + collectionName + ": findOneAndUpdate returned null");
        }
        Integer seq = result.getInteger("seq");
        if (seq == null) {
            throw new RuntimeException("Failed to get next sequence for " + collectionName + ": seq field is null");
        }
        return seq;
    }

    public static void close() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
