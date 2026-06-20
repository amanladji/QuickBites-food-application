package com.tap.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tap.daoimpl.MenuDAOImpl;
import com.tap.model.CartItem;
import com.tap.model.Menu;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            int menuId = Integer.parseInt(req.getParameter("menuId"));
            int restaurantId = Integer.parseInt(req.getParameter("restaurantId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            MenuDAOImpl mdao = new MenuDAOImpl();
            Menu menu = mdao.getMenu(menuId);
            if (menu == null) {
                resp.sendRedirect("menu?restaurantId=" + restaurantId);
                return;
            }

            @SuppressWarnings("unchecked")
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
                session.setAttribute("cart", cart);
            }

            boolean found = false;
            for (CartItem item : cart) {
                if (item.getMenuId() == menuId) {
                    item.setQuantity(item.getQuantity() + quantity);
                    found = true;
                    break;
                }
            }

            if (!found) {
                CartItem cartItem = new CartItem(menuId, restaurantId,
                        menu.getItemName(), menu.getDescription(),
                        menu.getPrice(), quantity);
                cart.add(cartItem);
            }

            resp.sendRedirect("menu?restaurantId=" + restaurantId);

        } else if ("update".equals(action)) {
            int menuId = Integer.parseInt(req.getParameter("menuId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            @SuppressWarnings("unchecked")
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart != null) {
                for (CartItem item : cart) {
                    if (item.getMenuId() == menuId) {
                        if (quantity <= 0) {
                            cart.remove(item);
                        } else {
                            item.setQuantity(quantity);
                        }
                        break;
                    }
                }
            }
            resp.sendRedirect("cart");

        } else if ("remove".equals(action)) {
            int menuId = Integer.parseInt(req.getParameter("menuId"));

            @SuppressWarnings("unchecked")
            List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
            if (cart != null) {
                cart.removeIf(item -> item.getMenuId() == menuId);
            }
            resp.sendRedirect("cart");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        double total = 0;
        for (CartItem item : cart) {
            total += item.getItemTotal();
        }

        req.setAttribute("cartTotal", total);
        RequestDispatcher rd = req.getRequestDispatcher("cart.jsp");
        rd.forward(req, resp);
    }
}
