package com.tap.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tap.daoimpl.OrderDAOImpl;
import com.tap.daoimpl.OrderItemDAOImpl;
import com.tap.model.CartItem;
import com.tap.model.Order;
import com.tap.model.OrderItem;
import com.tap.model.User;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect("cart");
            return;
        }

        double total = 0;
        int restaurantId = 0;
        for (CartItem item : cart) {
            total += item.getItemTotal();
            restaurantId = item.getRestaurantId();
        }

        req.setAttribute("cartTotal", total);
        req.setAttribute("restaurantId", restaurantId);
        req.setAttribute("cart", cart);

        RequestDispatcher rd = req.getRequestDispatcher("checkout.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect("cart");
            return;
        }

        String paymentMethod = req.getParameter("paymentMethod");
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            paymentMethod = "COD";
        }

        double total = 0;
        int restaurantId = 0;
        for (CartItem item : cart) {
            total += item.getItemTotal();
            restaurantId = item.getRestaurantId();
        }

        session.removeAttribute("cart");

        try {
            Order order = new Order(user.getUserId(), restaurantId, total, "Pending", paymentMethod);
            OrderDAOImpl odao = new OrderDAOImpl();
            odao.addOrder(order);

            OrderItemDAOImpl oidao = new OrderItemDAOImpl();
            for (CartItem item : cart) {
                OrderItem oi = new OrderItem(order.getOrderId(), item.getMenuId(), item.getItemName(), item.getQuantity(), item.getItemTotal());
                oidao.addItem(oi);
            }

            req.setAttribute("order", order);
            RequestDispatcher rd = req.getRequestDispatcher("orderConfirmation.jsp");
            rd.forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Order could not be processed. Please try again.");
            RequestDispatcher rd = req.getRequestDispatcher("checkout.jsp");
            rd.forward(req, resp);
        }
    }
}
