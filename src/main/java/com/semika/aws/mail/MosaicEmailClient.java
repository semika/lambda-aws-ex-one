package com.semika.aws.mail;

public interface MosaicEmailClient {

    void sendEmail(String toEmail, String msgContent, String subject);
}
