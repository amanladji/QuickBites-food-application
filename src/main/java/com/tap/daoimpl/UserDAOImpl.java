package com.tap.daoimpl;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.tap.DAO.UserDAO;
import com.tap.model.User;
import com.tap.utility.DBConnection;

public class UserDAOImpl implements UserDAO {

    private MongoCollection<Document> getCollection() {
        return DBConnection.getCollection("users");
    }

    @Override
    public void addUser(User u) {
        int userId = DBConnection.getNextSequence("users");
        Document doc = new Document("userId", userId)
                .append("userName", u.getUserName())
                .append("email", u.getEmail())
                .append("password", u.getPassword())
                .append("address", u.getAddress())
                .append("role", u.getRole())
                .append("createdDate", new Date())
                .append("lastLoginDate", new Date());
        InsertOneResult result = getCollection().insertOne(doc);
        u.setUserId(userId);
    }

    @Override
    public void updateUser(User u) {
        Document doc = new Document("userName", u.getUserName())
                .append("email", u.getEmail())
                .append("password", u.getPassword())
                .append("address", u.getAddress())
                .append("role", u.getRole());
        getCollection().updateOne(eq("userId", u.getUserId()), new Document("$set", doc));
    }

    @Override
    public void removeUser(int id) {
        DeleteResult result = getCollection().deleteOne(eq("userId", id));
    }

    @Override
    public User getUser(int id) {
        Document doc = getCollection().find(eq("userId", id)).first();
        return doc != null ? documentToUser(doc) : null;
    }

    @Override
    public User getUserByEmail(String email) {
        Document doc = getCollection().find(eq("email", email)).first();
        return doc != null ? documentToUser(doc) : null;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        for (Document doc : getCollection().find()) {
            list.add(documentToUser(doc));
        }
        return list;
    }

    private User documentToUser(Document doc) {
        return new User(
                doc.getInteger("userId"),
                doc.getString("userName"),
                doc.getString("email"),
                doc.getString("password"),
                doc.getString("address"),
                doc.getString("role"),
                doc.getDate("createdDate") != null
                        ? new java.sql.Timestamp(doc.getDate("createdDate").getTime()) : null,
                doc.getDate("lastLoginDate") != null
                        ? new java.sql.Timestamp(doc.getDate("lastLoginDate").getTime()) : null
        );
    }
}
