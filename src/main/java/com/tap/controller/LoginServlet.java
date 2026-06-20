package com.tap.controller;

import java.io.IOException;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tap.daoimpl.UserDAOImpl;
import com.tap.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (password == null || password.isEmpty()) {
            req.setAttribute("error", "Please enter your password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        try {
            UserDAOImpl udao = new UserDAOImpl();
            User user = udao.getUserByEmail(email);

            if (user != null && user.getPassword() != null &&
                BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                resp.sendRedirect("restaurants");
            } else {
                req.setAttribute("error", "Invalid email or password");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Login failed: " + e.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
