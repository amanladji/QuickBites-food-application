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
import com.tap.model.Order;
import com.tap.model.User;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        OrderDAOImpl odao = new OrderDAOImpl();
        List<Order> orderList = odao.getOrdersByUserId(user.getUserId());

        req.setAttribute("orderList", orderList);
        RequestDispatcher rd = req.getRequestDispatcher("orders.jsp");
        rd.forward(req, resp);
    }
}
