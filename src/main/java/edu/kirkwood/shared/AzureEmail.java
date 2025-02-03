package edu.kirkwood.shared;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.EmailClientBuilder;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import org.jsoup.Jsoup;

public class AzureEmail {
    public static EmailClient getEmailClient() {
        String connectionString = Config.getEnv("AZURE_EMAIL_CONNECTION");

        EmailClient emailClient = new EmailClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        return emailClient;
    }
    public static String sendEmail(String toEmailAddress, String subject, String bodyHTML) {
        EmailClient emailClient = getEmailClient();
        String body = Jsoup.parse(bodyHTML).text();
        EmailMessage message = new EmailMessage()
                .setSenderAddress(Config.getEnv("AZURE_EMAIL_FROM"))
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
}
