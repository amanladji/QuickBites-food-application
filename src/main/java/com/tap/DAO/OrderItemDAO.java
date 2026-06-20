package com.tap.DAO;

import java.util.List;

import com.tap.model.OrderItem;

public interface OrderItemDAO {
    void addItem(OrderItem oi);
    void updateItem(OrderItem oi);
    void removeItem(int orderItemId);
    OrderItem getUser(int orderItemId);
    List<OrderItem> getItemsByOrderId(int orderId);
    List<OrderItem> getAll();
}
