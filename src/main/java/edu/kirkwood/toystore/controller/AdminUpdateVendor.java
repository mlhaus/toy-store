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

@WebServlet(value="/update-vendor")
public class AdminUpdateVendor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active") || !userFromSession.getPrivileges().equals("admin")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String id = req.getParameter("id");
        req.setAttribute("id", id);
        Vendor vendor = VendorDAO.getVendor(id);
        req.setAttribute("vendor", vendor);
        req.getRequestDispatcher("WEB-INF/ecommerce/admin-update-vendor.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active") || !userFromSession.getPrivileges().equals("admin")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String id = req.getParameter("id"); // Vendor id from the hidden field
        req.setAttribute("id", id);
        String vend_id = req.getParameter("vend_id"); // Vendor id from the form
        String vend_name = req.getParameter("vend_name");
        String streetAddress = req.getParameter("address");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        String zip = req.getParameter("zip");
        String country = req.getParameter("country");

        boolean validationError = false;

        Vendor vendorOriginal = VendorDAO.getVendor(id); // Get the vendor from the data in the hidden field
        Vendor vendorNew = VendorDAO.getVendor(id);
        if(vendorNew == null) {
            req.setAttribute("vendorUpdated", false);
            req.setAttribute("vendorUpdatedMessage", "Failed to update vendor!");
        }


        try {
            vendorNew.setVend_name(vend_name);
            req.setAttribute("vendorNameError", false);
            req.setAttribute("vendorNameMessage", "Looks good!");
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("vendorNameError", true);
            req.setAttribute("vendorNameMessage", e.getMessage());
        }

        try {
            vendorNew.getAddress().setCountry(country);
            req.setAttribute("countryError", false);
            req.setAttribute("countryMessage", "Looks good!");
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("countryError", true);
            req.setAttribute("countryMessage", e.getMessage());
        }

        try {
            vendorNew.getAddress().setAddress(streetAddress);
            req.setAttribute("addressError", false);
            req.setAttribute("addressMessage", "Looks good!");
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("addressError", true);
            req.setAttribute("addressMessage", e.getMessage());
        }

        try {
            vendorNew.getAddress().setZip(zip);
            // Validate the country first
            if(vendorNew.getAddress().getCountry() != null) {
                req.setAttribute("zipError", false);
                req.setAttribute("zipMessage", "Looks good!");
            }
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("zipError", true);
            req.setAttribute("zipMessage", e.getMessage());
        }

        try {
            vendorNew.getAddress().setCity(city);
            // Validate the country first
            if(vendorNew.getAddress().getCountry() != null) {
                req.setAttribute("cityError", false);
                req.setAttribute("cityMessage", "Looks good!");
            }
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("cityError", true);
            req.setAttribute("cityMessage", e.getMessage());
        }

        try {
            vendorNew.getAddress().setState(state);
            // Validate the country first
            if(vendorNew.getAddress().isUnitedStates()) {
                req.setAttribute("stateError", false);
                req.setAttribute("stateMessage", "Looks good!");
            }
        } catch(IllegalArgumentException e) {
            validationError = true;
            req.setAttribute("stateError", true);
            req.setAttribute("stateMessage", e.getMessage());
        }

        req.setAttribute("vendor", vendorNew);

        if(!validationError) {
            boolean vendorUpdated = VendorDAO.updateVendor(vendorOriginal, vendorNew);
            req.setAttribute("vendorUpdated", vendorUpdated);
            if(vendorUpdated) {
                req.setAttribute("vendorUpdatedMessage", "Successfully updated vendor!");
            } else {
                req.setAttribute("vendorUpdatedMessage", "Failed to update vendor!");
            }
        }


        req.getRequestDispatcher("WEB-INF/ecommerce/admin-update-vendor.jsp").forward(req, resp);
    }
}
