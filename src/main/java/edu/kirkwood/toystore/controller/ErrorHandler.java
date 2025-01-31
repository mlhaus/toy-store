package edu.kirkwood.toystore.controller;

import edu.kirkwood.shared.Config;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.azure.communication.email.*;
import com.azure.communication.email.models.*;
import com.azure.core.util.polling.SyncPoller;

import static jakarta.servlet.RequestDispatcher.*;

@WebServlet("/errorHandler")
public class ErrorHandler extends HttpServlet {
    // Sends error emeail when web.xml debugging is false and doesn't when it is true

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check if debugging is enabled (set as a context parameter in web.xml)
        boolean isDebugging = Boolean.parseBoolean(getServletContext().getInitParameter("debugging"));
        System.out.println("IsDebugging: " + isDebugging);
        // Get error details
        String errorCode = String.valueOf(req.getAttribute(ERROR_STATUS_CODE));
        String exceptionType = String.valueOf(req.getAttribute(ERROR_EXCEPTION_TYPE));
        String errorMessage = String.valueOf(req.getAttribute(ERROR_MESSAGE));
        String servletName = req.getAttribute(ERROR_SERVLET_NAME) != null ? req.getAttribute(ERROR_SERVLET_NAME).toString() : "Unknown Servlet";
        String requestUri = req.getAttribute(ERROR_REQUEST_URI) != null ? req.getAttribute(ERROR_REQUEST_URI).toString() : "Unknown URI";

        // Construct error message for email
        String emailBodyHtml = String.format("""
        <html>
            <body>
                <h2>Error Notification</h2>
                <p><strong>Error Code:</strong> %s</p>
                <p><strong>Exception Type:</strong> %s</p>
                <p><strong>Message:</strong> %s</p>
                <p><strong>Servlet Name:</strong> %s</p>
                <p><strong>Request URI:</strong> %s</p>
            </body>
        </html>
        """, errorCode, exceptionType, errorMessage, servletName, requestUri);

        // Send email if debugging is NOT enabled
        if (!isDebugging) {
            sendErrorEmail(emailBodyHtml);
        }

        // Set error message attributes
        String errorMsg = "<strong>Error code:</strong> " + errorCode + "<br>";
        errorMsg += "<strong>Exception:</strong> " + exceptionType + "<br>";
        errorMsg += "<strong>Message:</strong> " + errorMessage + "<br>";
        errorMsg += "<strong>Servlet Name:</strong> " + servletName + "<br>";
        errorMsg += "<strong>Request URI:</strong> " + requestUri + "<br>";

        req.setAttribute("errorMsg", errorMsg);
        req.setAttribute("pageTitle", "Error");

        // Forward to error page
        req.getRequestDispatcher("WEB-INF/error.jsp").forward(req, resp);
    }

    private void sendErrorEmail(String emailBodyHtml) {
        // Connection to Azure
        final String CONNECTION_STRING = Config.getEnv("AZURE_EMAIL_CONNECTION");
        // Sender's information
        final String SENDER_EMAIL = Config.getEnv("AZURE_EMAIL_FROM");
        // Admin email (recipient)
        final String ADMIN_EMAIL = Config.getEnv("ADMIN_EMAIL");

        try {
            EmailClient emailClient = new EmailClientBuilder().connectionString(CONNECTION_STRING).buildClient();

            EmailMessage emailMessage = new EmailMessage()
                    .setSenderAddress(SENDER_EMAIL)
                    .setToRecipients(new EmailAddress(ADMIN_EMAIL))
                    .setSubject("Error Notification")
                    .setBodyHtml(emailBodyHtml);

            SyncPoller<EmailSendResult, EmailSendResult> poller = emailClient.beginSend(emailMessage, null);
            poller.waitForCompletion(); // Wait for email to send
        } catch (Exception e) {
            e.printStackTrace(); // Log email sending failure
        }
    }
}


//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import static jakarta.servlet.RequestDispatcher.*;
//import java.io.IOException;
//
//@WebServlet("/errorHandler")
//public class ErrorHandler extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String errorMsg = "<strong>Error code:</strong> " + req.getAttribute(ERROR_STATUS_CODE) + "<br>";
//        errorMsg += "<strong>Exception:</strong> " + req.getAttribute(ERROR_EXCEPTION_TYPE) + "<br>";
//        errorMsg += "<strong>Message:</strong> " + req.getAttribute(ERROR_MESSAGE) + "<br>";
//        errorMsg += "<strong>Servlet:</strong> " + req.getAttribute(ERROR_SERVLET_NAME) + "<br>";
//        errorMsg += "<strong>Request URI:</strong> " + req.getAttribute(ERROR_REQUEST_URI) + "<br>";
//        req.setAttribute("errorMsg", errorMsg);
//        req.setAttribute("pageTitle", "Error");
//        req.getRequestDispatcher("WEB-INF/error.jsp").forward(req, resp);
//    }
//}