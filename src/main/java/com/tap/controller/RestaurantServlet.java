package com.tap.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.tap.daoimpl.RestaurantDAOImpl;
import com.tap.model.Restaurant;

@WebServlet("/restaurants")
public class RestaurantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RestaurantDAOImpl rdao = new RestaurantDAOImpl();
        List<Restaurant> restaurantList = rdao.getAll();
        req.setAttribute("restaurantList", restaurantList);
        RequestDispatcher rd = req.getRequestDispatcher("restaurant.jsp");
        rd.forward(req, resp);
    }
}
