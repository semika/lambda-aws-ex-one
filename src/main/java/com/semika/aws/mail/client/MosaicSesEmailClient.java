package com.semika.aws.mail.client;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sesv2.SesV2Client;
import software.amazon.awssdk.services.sesv2.model.*;

public class MosaicSesEmailClient implements MosaicEmailClient {

    @Override
    public void sendEmail(String toEmail, String msgContent, String subject) {

        String sender = "semika.siriwardana@gmail.com";

        Region region = Region.AP_SOUTHEAST_1;

        SesV2Client sesv2Client = SesV2Client.builder()
                .region(region)
                //.credentialsProvider(mosaicAWSCredentialProvier)
                .build();

        // The HTML body of the email.
        String bodyHTML = "<html>" + "<head></head>" + "<body>" + "<h1>Hello!</h1>"
                + "<p> See the list of customers.</p>" + "</body>" + "</html>";


        Destination destination = Destination.builder()
                .toAddresses(toEmail)
                .build();

        Content content = Content.builder()
                .data(bodyHTML)
                .build();
        Body body = Body.builder()
                .html(content)
                .build();

        Content sub = Content.builder()
                .data(subject)
                .build();

        Message msg = Message.builder()
                .subject(sub)
                .body(body)
                .build();

        EmailContent emailContent = EmailContent.builder()
                .simple(msg)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .content(emailContent)
                .fromEmailAddress(sender)
                .build();

        sesv2Client.sendEmail(emailRequest);

    }

    public static MosaicSesEmailClient of() {
        return new MosaicSesEmailClient();
    }
}
