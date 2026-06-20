package com.tap.daoimpl;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.tap.DAO.MenuDAO;
import com.tap.model.Menu;
import com.tap.utility.DBConnection;

public class MenuDAOImpl implements MenuDAO {

    private MongoCollection<Document> getCollection() {
        return DBConnection.getCollection("menus");
    }

    @Override
    public void addMenu(Menu m) {
        int menuId = DBConnection.getNextSequence("menus");
        Date now = new Date();
        Document doc = new Document("menuId", menuId)
                .append("restaurantId", m.getRestaurantId())
                .append("itemName", m.getItemName())
                .append("description", m.getDescription())
                .append("price", m.getPrice())
                .append("isAvailable", m.getIsAvailable())
                .append("category", m.getCategory())
                .append("imageUrl", "")
                .append("createdAt", now)
                .append("updatedAt", now)
                .append("deleteAt", null);
        getCollection().insertOne(doc);
        m.setMenuId(menuId);
    }

    @Override
    public void updateMenu(Menu m) {
        Document doc = new Document("restaurantId", m.getRestaurantId())
                .append("itemName", m.getItemName())
                .append("description", m.getDescription())
                .append("price", m.getPrice())
                .append("isAvailable", m.getIsAvailable())
                .append("category", m.getCategory())
                .append("updatedAt", new Date());
        getCollection().updateOne(eq("menuId", m.getMenuId()), new Document("$set", doc));
    }

    @Override
    public void removeMenu(int menuId) {
        getCollection().deleteOne(eq("menuId", menuId));
    }

    @Override
    public Menu getMenu(int menuId) {
        Document doc = getCollection().find(eq("menuId", menuId)).first();
        return doc != null ? documentToMenu(doc) : null;
    }

    @Override
    public List<Menu> getMenuByRestaurantId(int restaurantId) {
        List<Menu> list = new ArrayList<>();
        for (Document doc : getCollection().find(eq("restaurantId", restaurantId))) {
            list.add(documentToMenu(doc));
        }
        return list;
    }

    @Override
    public List<Menu> getAll() {
        List<Menu> list = new ArrayList<>();
        for (Document doc : getCollection().find()) {
            list.add(documentToMenu(doc));
        }
        return list;
    }

    private double getDouble(Document doc, String field) {
        Number n = doc.get(field, Number.class);
        return n != null ? n.doubleValue() : 0.0;
    }

    private Menu documentToMenu(Document doc) {
        return new Menu(
                doc.getInteger("menuId"),
                doc.getInteger("restaurantId"),
                doc.getString("itemName"),
                doc.getString("description"),
                getDouble(doc, "price"),
                doc.getInteger("isAvailable"),
                doc.getString("category"),
                doc.getDate("createdAt") != null
                        ? new java.sql.Timestamp(doc.getDate("createdAt").getTime()) : null,
                doc.getDate("updatedAt") != null
                        ? new java.sql.Timestamp(doc.getDate("updatedAt").getTime()) : null,
                null
        );
    }
}
