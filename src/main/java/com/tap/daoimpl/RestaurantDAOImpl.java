package com.tap.daoimpl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.tap.DAO.RestaurantDAO;
import com.tap.model.Restaurant;
import com.tap.utility.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {

    private MongoCollection<Document> getCollection() {
        return DBConnection.getCollection("restaurants");
    }

    @Override
    public void addRestaurant(Restaurant r) {
        int restaurantId = DBConnection.getNextSequence("restaurants");
        Document doc = new Document("restaurantId", restaurantId)
                .append("name", r.getName())
                .append("cuisineType", r.getCuisineType())
                .append("deliveryTime", r.getDeliveryTime())
                .append("address", r.getAddress())
                .append("adminUserId", r.getAdminUserId())
                .append("rating", r.getRating())
                .append("isActive", r.getIsActive())
                .append("imageUrl", "");
        getCollection().insertOne(doc);
        r.setRestaurantId(restaurantId);
    }

    @Override
    public void updateRestaurant(Restaurant r) {
        Document doc = new Document("name", r.getName())
                .append("cuisineType", r.getCuisineType())
                .append("deliveryTime", r.getDeliveryTime())
                .append("address", r.getAddress())
                .append("adminUserId", r.getAdminUserId())
                .append("rating", r.getRating())
                .append("isActive", r.getIsActive());
        getCollection().updateOne(eq("restaurantId", r.getRestaurantId()), new Document("$set", doc));
    }

    @Override
    public void removeRestaurant(int restaurantId) {
        getCollection().deleteOne(eq("restaurantId", restaurantId));
    }

    @Override
    public Restaurant getRestaurant(int restaurantId) {
        Document doc = getCollection().find(eq("restaurantId", restaurantId)).first();
        return doc != null ? documentToRestaurant(doc) : null;
    }

    @Override
    public List<Restaurant> getAll() {
        List<Restaurant> list = new ArrayList<>();
        for (Document doc : getCollection().find().sort(descending("rating"))) {
            list.add(documentToRestaurant(doc));
        }
        return list;
    }

    private int getInt(Document doc, String field, int defaultValue) {
        Integer val = doc.getInteger(field);
        return val != null ? val : defaultValue;
    }

    private Restaurant documentToRestaurant(Document doc) {
        return new Restaurant(
                getInt(doc, "restaurantId", 0),
                doc.getString("name"),
                doc.getString("cuisineType"),
                getInt(doc, "deliveryTime", 0),
                doc.getString("address"),
                getInt(doc, "adminUserId", 0),
                doc.get("rating", Number.class) != null ? doc.get("rating", Number.class).doubleValue() : 0.0,
                getInt(doc, "isActive", 1)
        );
    }
}
