package com.semika.aws.mail.client;

public interface MosaicEmailClient {

    void sendEmail(String toEmail, String msgContent, String subject);
}
