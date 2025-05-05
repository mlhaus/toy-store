package edu.kirkwood.shared;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;

import java.net.URI;
import java.util.Arrays;

public class MyTwilio {
    public static void init() {
        String twilioSid = Config.getEnv("TWILIO_SID");
        String twilioAuthToken = Config.getEnv("TWILIO_AUTH_TOKEN");
        Twilio.init(twilioSid, twilioAuthToken);
    }

    public static String sendSMS(String phoneNumber, String msg) {
        init();
        String result = "";
        try {
            Message message = Message.creator(
                new PhoneNumber("+1" + phoneNumber) // to
                , new PhoneNumber(Config.getEnv("TWILIO_PHONE")) // from
                , msg
//            ).setMediaUrl(
//                Arrays.asList(
//                    URI.create("https://c1.staticflickr.com/3/2899/14341091933_1e92e62d12_b.jpg")
//                )
            ).create();
            result = message.getSid();
        } catch(ApiException e) {
            result = e.getMessage();
        }
        return result;
    }

    public static String sendVoice(String phoneNumber, String msg) {
        init();
        String result = "";
        try {
            String twiml = ""
                    + "<Response>"
                    + "<Pause length=\"2\" />"
                    + "<Say voice=\"Polly.Joanna-Neural\">" + msg + "</Say>"
                    + "<Play>https://demo.twilio.com/docs/classic.mp3</Play>"
                    + "</Response>";
            Call call = Call.creator(
                new PhoneNumber("+1" + phoneNumber) // to
                , new PhoneNumber(Config.getEnv("TWILIO_PHONE")) // from
                , new Twiml(twiml)
            ).create();
            result = call.getSid();
        } catch (final ApiException e) {
            result = e.getMessage();
        }
        return result;
    }
}
