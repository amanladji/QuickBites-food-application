package com.tap.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.tap.daoimpl.OrderItemDAOImpl;
import com.tap.model.OrderItem;

@WebServlet("/orderItems")
public class OrderItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdParam = req.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.isEmpty()) {
            resp.sendRedirect("orders");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdParam);
        } catch (NumberFormatException e) {
            resp.sendRedirect("orders");
            return;
        }

        OrderItemDAOImpl oidao = new OrderItemDAOImpl();
        List<OrderItem> orderItemList = oidao.getItemsByOrderId(orderId);

        req.setAttribute("orderItemList", orderItemList);
        RequestDispatcher rd = req.getRequestDispatcher("orderItems.jsp");
        rd.forward(req, resp);
    }
}
