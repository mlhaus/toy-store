package edu.kirkwood.toystore.controller;

import edu.kirkwood.toystore.model.Product;
import edu.kirkwood.toystore.model.ProductCategory;
import edu.kirkwood.toystore.model.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/shop")
public class Shop extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int limit = 5;
        int offset = 0;
        String categories = "1,2";
        List<Product> products = ProductDAO.getProducts(limit, offset, categories);
        req.setAttribute("products", products);
        List<ProductCategory> productCategories = ProductDAO.getAllCategories();
        req.setAttribute("productCategories", productCategories);
        req.getRequestDispatcher("WEB-INF/ecommerce/shop.jsp").forward(req, resp);
    }
}
