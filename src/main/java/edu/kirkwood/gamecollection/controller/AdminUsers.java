package edu.kirkwood.gamecollection.controller;

import edu.kirkwood.gamecollection.model.User;
import edu.kirkwood.gamecollection.model.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/dat-users")
public class AdminUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        HttpSession session = req.getSession();
//        User userFromSession = (User) session.getAttribute("activeUser");
//        if (userFromSession == null || !userFromSession.getStatus().equals("active") || !userFromSession.getPrivileges().equals("admin")) {
//
//        }
        //throw new ServletException("This is an error implemented");
        List<User> users = UserDAO.getAll();
        req.setAttribute("users", users);
        req.setAttribute("pageTitle", "All Users");
        req.getRequestDispatcher("WEB-INF/dat/admin-users.jsp").forward(req, resp);
    }
}
