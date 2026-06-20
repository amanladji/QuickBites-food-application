package com.tap.controller;

import java.io.IOException;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.tap.daoimpl.UserDAOImpl;
import com.tap.model.User;

@WebServlet("/register")
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");
        String role = req.getParameter("role");

        if (userName == null || userName.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.isEmpty()) {
            req.setAttribute("error", "Name, email, and password are required.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        if (role == null || role.isEmpty()) role = "customer";

        try {
            UserDAOImpl udao = new UserDAOImpl();
            User existing = udao.getUserByEmail(email);
            if (existing != null) {
                req.setAttribute("error", "Email already registered. Please login.");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
            }

            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

            User u = new User(userName, email, hashedPassword, address, role);
            udao.addUser(u);

            req.setAttribute("message", "Registration successful! Please login.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Registration failed: " + e.getMessage());
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}
