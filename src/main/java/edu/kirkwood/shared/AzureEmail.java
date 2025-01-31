package edu.kirkwood.shared;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import io.github.cdimascio.dotenv.Dotenv;
import org.jsoup.Jsoup;

public class AzureEmail {
    public static EmailClient getEmailClient() {
        String connectionString = Dotenv.load().get("AZURE_EMAIL_CONNECTION");

        EmailClient emailClient = new EmailClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        return emailClient;
    }
    public static String sendEmail(String toEmailAddress, String subject, String bodyHTML) {
        EmailClient emailClient = getEmailClient();
        String body = Jsoup.parse(bodyHTML).text();
        EmailMessage message = new EmailMessage()
                .setSenderAddress(Dotenv.load().get("AZURE_EMAIL_FROM"))
                .setToRecipients(toEmailAddress)
                .setSubject(subject)
                .setBodyPlainText(body)
                .setBodyHtml(bodyHTML);
        String error = "";
        SyncPoller<EmailSendResult, EmailSendResult> poller = null;
        try {
            poller = emailClient.beginSend(message);
        } catch(RuntimeException e) {
            error = e.getMessage();
        }
        PollResponse<EmailSendResult> response = poller.waitForCompletion();
//        System.out.println("Operation Id: " + response.getValue().getId());
        return error;
    }

    public static void main(String[] args) throws InterruptedException {
        // Pretend this is your doPost method
        // Get all parameters from form submission
        String toEmailAddress = "marc.hauschildt@gmail.com".trim(); // Get emailAddress parameter
        String subject = "Testing".trim(); // Get subject parameter
        String bodyHTML = "<h2>This is a test email</h2><p>Testing, Testing, Testing</p>".trim(); // Get messageBody parameter
        // Set attributes for each of these variables

        // Validate the inputs
        boolean error =false;
        if(toEmailAddress == null || !Validators.isValidEmail(toEmailAddress)) {
            // set emailAddress-error attribute
            System.out.println("Invalid email address: " + toEmailAddress);
            error = true;
        }
        if(subject == null || subject.isEmpty()) {
            // set subject-error attribute
            System.out.println("Subject is required");
            error = true;
        }
        if(bodyHTML == null || bodyHTML.isEmpty()) {
            // set messageBody-error attribute
            System.out.println("Body is required");
            error = true;
        }

        if(!error) {
//            String errorMessage = sendEmail(toEmailAddress, subject, bodyHTML);
            String toEmailAddress2 = "mlhauschildt@yahoo.com";
//            String errorMessage2 = sendEmail(toEmailAddress2, subject, bodyHTML);
            EmailThread emailThread1 = new EmailThread(toEmailAddress, subject, bodyHTML);
            emailThread1.start();
            EmailThread emailThread2 = new EmailThread(toEmailAddress2, subject, bodyHTML);
            emailThread2.start();
            emailThread1.join();
            emailThread2.join();
            String errorMessage = emailThread1.getErrorMessage();
            String errorMessage2 = emailThread2.getErrorMessage();
            if (errorMessage.isEmpty()) {
                // set send-success attribute
                System.out.println("Message sent to " + toEmailAddress);
            } else {
                // Set send-error attribute
                System.out.println("Message not sent to " + toEmailAddress + " - " + errorMessage);
            }
            if (errorMessage2.isEmpty()) {
                // set send-success attribute
                System.out.println("Message sent to " + toEmailAddress2);
            } else {
                // Set send-error attribute
                System.out.println("Message not sent to " + toEmailAddress2 + " - " + errorMessage2);
            }
        }
        // Forward to JSP
    }
}
