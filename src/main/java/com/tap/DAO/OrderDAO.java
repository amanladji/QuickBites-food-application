package com.tap.DAO;

import java.util.List;

import com.tap.model.Order;

public interface OrderDAO {
    void addOrder(Order o);
    void updateOrder(Order o);
    void removeOrder(int orderId);
    Order getOrder(int orderId);
    List<Order> getOrdersByUserId(int userId);
    List<Order> getAll();
}
