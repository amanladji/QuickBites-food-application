package com.tap.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.tap.daoimpl.MenuDAOImpl;
import com.tap.daoimpl.RestaurantDAOImpl;
import com.tap.model.Menu;
import com.tap.model.Restaurant;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String restaurantIdParam = req.getParameter("restaurantId");
        if (restaurantIdParam == null || restaurantIdParam.isEmpty()) {
            resp.sendRedirect("restaurants");
            return;
        }

        int restaurantId;
        try {
            restaurantId = Integer.parseInt(restaurantIdParam);
        } catch (NumberFormatException e) {
            resp.sendRedirect("restaurants");
            return;
        }

        MenuDAOImpl mdao = new MenuDAOImpl();
        List<Menu> menuList = mdao.getMenuByRestaurantId(restaurantId);

        RestaurantDAOImpl rdao = new RestaurantDAOImpl();
        Restaurant restaurant = rdao.getRestaurant(restaurantId);

        if (restaurant == null) {
            resp.sendRedirect("restaurants");
            return;
        }

        req.setAttribute("menuList", menuList);
        req.setAttribute("restaurant", restaurant);

        RequestDispatcher rd = req.getRequestDispatcher("menu.jsp");
        rd.forward(req, resp);
    }
}
