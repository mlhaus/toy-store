package edu.kirkwood.toystore.controller;

import edu.kirkwood.toystore.model.User;
import edu.kirkwood.toystore.model.Vendor;
import edu.kirkwood.toystore.model.VendorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/vendors")
public class AdminVendors extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active") || !userFromSession.getPrivileges().equals("admin")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        List<Vendor> vendors = VendorDAO.getVendors();
        req.setAttribute("vendors", vendors);
        req.getRequestDispatcher("WEB-INF/ecommerce/admin-vendors.jsp").forward(req, resp);
    }
}
