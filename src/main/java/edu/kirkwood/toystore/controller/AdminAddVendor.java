package edu.kirkwood.toystore.controller;

import edu.kirkwood.toystore.model.Address;
import edu.kirkwood.toystore.model.Vendor;
import edu.kirkwood.toystore.model.VendorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value="/add-vendor")
public class AdminAddVendor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/ecommerce/admin-add-vendor.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String vend_id = req.getParameter("vend_id");
        String vend_name = req.getParameter("vend_name");
        String streetAddress = req.getParameter("address");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        String zip = req.getParameter("zip");
        String country = req.getParameter("country");
        req.setAttribute("vend_id", vend_id);
        req.setAttribute("vend_name", vend_name);
        req.setAttribute("address", streetAddress);
        req.setAttribute("city", city);
        req.setAttribute("state", state);
        req.setAttribute("zip", zip);
        req.setAttribute("country", country);

        Vendor vendor = new Vendor();
        boolean validationError = false;

        Vendor vendorFromDB = VendorDAO.getVendor(vend_id);
        if(vendorFromDB != null) {
            validationError = true;
            req.setAttribute("vendorIdError", true);
            req.setAttribute("vendorIdMessage", "That vendor already exists");
        } else {
            try {
                vendor.setVend_id(vend_id);
                req.setAttribute("vendorIdError", false);
                req.setAttribute("vendorIdMessage", "Looks good!");
            } catch (IllegalArgumentException e) {
                validationError = true;
                req.setAttribute("vendorIdError", true);
                req.setAttribute("vendorIdMessage", e.getMessage());
            }
        }

        try {
            vendor.setVend_name(vend_name);
            req.setAttribute("vendorNameError", false);
            req.setAttribute("vendorNameMessage", "Looks good!");
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("vendorNameError", true);
            req.setAttribute("vendorNameMessage", e.getMessage());
        }

        Address address = new Address();

        try {
            address.setCountry(country);
            req.setAttribute("countryError", false);
            req.setAttribute("countryMessage", "Looks good!");
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("countryError", true);
            req.setAttribute("countryMessage", e.getMessage());
        }

        try {
            address.setAddress(streetAddress);
            req.setAttribute("addressError", false);
            req.setAttribute("addressMessage", "Looks good!");
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("addressError", true);
            req.setAttribute("addressMessage", e.getMessage());
        }

        try {
            address.setZip(zip);
            // Validate the country first
            if(address.getCountry() != null) {
                req.setAttribute("zipError", false);
                req.setAttribute("zipMessage", "Looks good!");
            }
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("zipError", true);
            req.setAttribute("zipMessage", e.getMessage());
        }

        try {
            address.setCity(city);
            // Validate the country first
            if(address.getCountry() != null) {
                req.setAttribute("cityError", false);
                req.setAttribute("cityMessage", "Looks good!");
            }
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("cityError", true);
            req.setAttribute("cityMessage", e.getMessage());
        }

        try {
            address.setState(state);
            // Validate the country first
            if(address.isUnitedStates()) {
                req.setAttribute("stateError", false);
                req.setAttribute("stateMessage", "Looks good!");
            }
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("stateError", true);
            req.setAttribute("stateMessage", e.getMessage());
        }

        vendor.setAddress(address);

        if(!validationError) {
            boolean vendorAdded = VendorDAO.addVendor(vendor);
            req.setAttribute("vendorAdded", vendorAdded);
            if(vendorAdded) {
                req.setAttribute("vendorAddedMessage", "Successfully added vendor!");
            } else {
                req.setAttribute("vendorAddedMessage", "Failed to add vendor!");
            }
        }


        req.getRequestDispatcher("WEB-INF/ecommerce/admin-add-vendor.jsp").forward(req, resp);
    }
}
