package edu.kirkwood.toystore.controller;

import edu.kirkwood.shared.MyTwilio;
import edu.kirkwood.shared.Validators;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/sms")
public class SendSMS extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Send SMS");
        req.getRequestDispatcher("WEB-INF/send-sms.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        String msg = req.getParameter("message");
        req.setAttribute("phone", phone);
        req.setAttribute("message", msg);
        HttpSession session = req.getSession();
        if(phone == null || phone.equals("")) {
            session.setAttribute("flashMessageDanger", "Phone number required");
        } else {
            String phoneOnlyDigits = phone.replaceAll("[^\\d.]", "");
            if(!Validators.isValidPhone(phoneOnlyDigits)) {
                session.setAttribute("flashMessageDanger", "Invalid US phone number");
            } else {
                if(msg == null || msg.equals("")) {
                    session.setAttribute("flashMessageDanger", "Message required");
                } else {
                    if(msg.length() > 160) {
                        session.setAttribute("flashMessageDanger", "Message too long");
                    } else {
                        String message = MyTwilio.sendSMS(phone, msg);
                        if(message.startsWith("SM")) {
                            session.setAttribute("flashMessageSuccess", "Message sent");
                        } else {
                            session.setAttribute("flashMessageDanger", message);
                        }
                    }
                }
            }
        }
        req.setAttribute("pageTitle", "Send SMS");
        req.getRequestDispatcher("WEB-INF/send-sms.jsp").forward(req, resp);
    }
}
