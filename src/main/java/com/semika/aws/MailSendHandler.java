package com.semika.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.semika.aws.mail.MosaicSesEmailClient;

import java.util.Map;

public class MailSendHandler implements RequestHandler<Map<String,String>, String> {
    @Override
    public String handleRequest(Map<String, String> dataMap, Context context) {
        String email = dataMap.get("email");
        sendEmail(email);
        return "Email sent to : " + email;
    }

    private void sendEmail(String email) {
        MosaicSesEmailClient.of().sendEmail(email, "This is test email", "Test email");
    }
}
