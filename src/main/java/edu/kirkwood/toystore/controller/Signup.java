package edu.kirkwood.toystore.controller;

import edu.kirkwood.toystore.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signup")
public class Signup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Sign up for an account");
        req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        String[] terms = req.getParameterValues("terms");
        req.setAttribute("email", email);
        req.setAttribute("password1", password1);
        req.setAttribute("password2", password2);
        req.setAttribute("terms", terms != null && terms[0].equals("agree") ? "agree" : "");
        req.setAttribute("pageTitle", "Sign up for an account");
        
        User user = new User();
        boolean errorFound = false;
        try {
            user.setEmail(email);
        } catch (IllegalArgumentException e) {
            errorFound = true;
            req.setAttribute("emailError", e.getMessage());
        }
        if(UserDAO.get(email) != null) {
            errorFound = true;
            req.setAttribute("emailError", "A user with that email already exists. Please login or reset your password.");
        }
        try {
            user.setPassword(password1.toCharArray());
        } catch(IllegalArgumentException e) {
            errorFound = true;
            req.setAttribute("password1Error", e.getMessage());
        }
        if(password2 != null && password2.equals("")) {
            errorFound = true;
            req.setAttribute("password2Error", "Please confirm your password");
        }
        if(password1 != null && password2 != null && !password2.equals(password1)) {
            errorFound = true;
            req.setAttribute("password2Error", "Passwords don't match");
        }
        if(terms == null || !terms[0].equals("agree")) {
            errorFound = true;
            req.setAttribute("termsError", "You must agree to our terms of use");
        }
        
        
        
        req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req, resp);
    }
}
