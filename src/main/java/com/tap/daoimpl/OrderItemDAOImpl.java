package com.tap.daoimpl;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.tap.DAO.OrderItemDAO;
import com.tap.model.OrderItem;
import com.tap.utility.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    private MongoCollection<Document> getCollection() {
        return DBConnection.getCollection("orderItems");
    }

    @Override
    public void addItem(OrderItem oi) {
        int orderItemId = DBConnection.getNextSequence("orderItems");
        Document doc = new Document("orderItemId", orderItemId)
                .append("orderId", oi.getOrderId())
                .append("menuId", oi.getMenuId())
                .append("itemName", oi.getItemName())
                .append("quantity", oi.getQuantity())
                .append("itemTotal", oi.getItemTotal());
        getCollection().insertOne(doc);
        oi.setOrderItemId(orderItemId);
    }

    @Override
    public void updateItem(OrderItem oi) {
        Document doc = new Document("menuId", oi.getMenuId())
                .append("quantity", oi.getQuantity())
                .append("itemTotal", oi.getItemTotal());
        getCollection().updateOne(eq("orderItemId", oi.getOrderItemId()), new Document("$set", doc));
    }

    @Override
    public void removeItem(int orderItemId) {
        getCollection().deleteOne(eq("orderItemId", orderItemId));
    }

    @Override
    public OrderItem getUser(int orderItemId) {
        Document doc = getCollection().find(eq("orderItemId", orderItemId)).first();
        return doc != null ? documentToOrderItem(doc) : null;
    }

    @Override
    public List<OrderItem> getItemsByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        for (Document doc : getCollection().find(eq("orderId", orderId))) {
            list.add(documentToOrderItem(doc));
        }
        return list;
    }

    @Override
    public List<OrderItem> getAll() {
        List<OrderItem> list = new ArrayList<>();
        for (Document doc : getCollection().find()) {
            list.add(documentToOrderItem(doc));
        }
        return list;
    }

    private double getDouble(Document doc, String field) {
        Number n = doc.get(field, Number.class);
        return n != null ? n.doubleValue() : 0.0;
    }

    private OrderItem documentToOrderItem(Document doc) {
        return new OrderItem(
                doc.getInteger("orderItemId"),
                doc.getInteger("orderId"),
                doc.getInteger("menuId"),
                doc.getString("itemName"),
                doc.getInteger("quantity"),
                getDouble(doc, "itemTotal")
        );
    }
}
