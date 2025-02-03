package edu.kirkwood.shared;

import com.azure.communication.email.EmailClient;
import com.azure.communication.email.models.EmailMessage;
import com.azure.communication.email.models.EmailSendResult;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.SyncPoller;
import okhttp3.*;
import org.jsoup.Jsoup;

import java.io.IOException;

public class MailtrapEmail {
    public static String sendEmail(String toEmailAddress, String subject, String bodyHTML) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"from\":{\"email\":\"hello@example.com\",\"name\":\"Mailtrap Test\"},\"to\":[{\"email\":\"marc.hauschildt@kirkwood.edu\"}],\"subject\":\"You are awesome!\",\"text\":\"Congrats for sending test email with Mailtrap!\",\"category\":\"Integration Test\"}", mediaType);
        Request request = new Request.Builder()
                .url("https://sandbox.api.mailtrap.io/api/send/2541140")
                .method("POST", body)
                .addHeader("Authorization", "Bearer 9a7fa0723b331f4eec4d5d5e5a9ba0ea")
                .addHeader("Content-Type", "application/json")
                .build();
        String error = "";
        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            error = "Email not sent";
        }
        return error;
    }

    public static void main(String[] args) {
        System.out.println(sendEmail("", "", ""));
    }
}
