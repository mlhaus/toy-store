package edu.kirkwood.toystore.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value="/vendors")
public class AdminVendors extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Call the VendorDAO to get List<Vendor>
        req.getRequestDispatcher("WEB-INF/ecommerce/admin-vendors.jsp").forward(req, resp);
    }
}
