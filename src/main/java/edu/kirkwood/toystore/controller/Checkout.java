package edu.kirkwood.toystore.controller;

import edu.kirkwood.toystore.model.ShoppingCart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/checkout")
public class Checkout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCart cart = (ShoppingCart)req.getAttribute("cart");
        double salesTaxRate = 0.07;
        double shippingCost = 0;
        double discountPercent = 0;
        req.setAttribute("pageTitle", "Checkout");
        req.getRequestDispatcher("WEB-INF/ecommerce/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] billingInfo = new String[8];
        billingInfo[0] = "Marc";
        billingInfo[1] = "Hauschildt";
        billingInfo[2]  = "marc.hauschildt@kirkwood.edu";
        billingInfo[3]  = "6301 Kirkwood Blvd SW";
        billingInfo[4]  = "Cedar Rapids";
        billingInfo[5]  = "IA";
        billingInfo[6]  = "52404";
        billingInfo[7]  = "";

        String[] sameAddressCheck = req.getParameterValues("same_address");
        boolean sameAddress = false;
        if(sameAddressCheck != null) {
            sameAddress = true;
        }

        String[] ccInfo = new String[4];
        ccInfo[0] = "Marc Hauschildt";
        ccInfo[1] = "4242424242424242";
        ccInfo[2] = "1225";
        ccInfo[3] = "111";
        
    }
}
