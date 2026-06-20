package com.tap.daoimpl;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.tap.DAO.OrderDAO;
import com.tap.model.Order;
import com.tap.utility.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    private MongoCollection<Document> getCollection() {
        return DBConnection.getCollection("orders");
    }

    @Override
    public void addOrder(Order o) {
        int orderId = DBConnection.getNextSequence("orders");
        Document doc = new Document("orderId", orderId)
                .append("userId", o.getUserId())
                .append("restaurentId", o.getRestaurentId())
                .append("orderDate", new Date())
                .append("totalAmount", o.getTotalAmount())
                .append("status", o.getStatus())
                .append("paymentMethod", o.getPaymentMethod());
        getCollection().insertOne(doc);
        o.setOrderId(orderId);
    }

    @Override
    public void updateOrder(Order o) {
        Document doc = new Document("status", o.getStatus())
                .append("paymentMethod", o.getPaymentMethod());
        getCollection().updateOne(eq("orderId", o.getOrderId()), new Document("$set", doc));
    }

    @Override
    public void removeOrder(int orderId) {
        getCollection().deleteOne(eq("orderId", orderId));
    }

    @Override
    public Order getOrder(int orderId) {
        Document doc = getCollection().find(eq("orderId", orderId)).first();
        return doc != null ? documentToOrder(doc) : null;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        for (Document doc : getCollection().find(eq("userId", userId))) {
            list.add(documentToOrder(doc));
        }
        return list;
    }

    @Override
    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();
        for (Document doc : getCollection().find()) {
            list.add(documentToOrder(doc));
        }
        return list;
    }

    private double getDouble(Document doc, String field) {
        Number n = doc.get(field, Number.class);
        return n != null ? n.doubleValue() : 0.0;
    }

    private Order documentToOrder(Document doc) {
        return new Order(
                doc.getInteger("orderId"),
                doc.getInteger("userId"),
                doc.getInteger("restaurentId"),
                doc.getDate("orderDate") != null
                        ? new java.sql.Timestamp(doc.getDate("orderDate").getTime()) : null,
                getDouble(doc, "totalAmount"),
                doc.getString("status"),
                doc.getString("paymentMethod")
        );
    }
}
