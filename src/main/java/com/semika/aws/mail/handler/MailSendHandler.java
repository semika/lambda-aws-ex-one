package com.semika.aws.mail.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.semika.aws.mail.client.MosaicSesEmailClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.*;

import java.util.Map;

@Slf4j
public class MailSendHandler implements RequestHandler<Map<String,String>, String> {

    @Override
    public String handleRequest(Map<String, String> dataMap, Context context) {
        String email = dataMap.get("email");
        log.info("Email:" + email);
        try {
            sendEmail(email);
            sendEmail(getExtraEmailFromSSM());
            return "Email sent to : " + email;
        } catch (Exception e) {
            sendEmail(email);
            return "Sending mail failed to : " + email;
        }

    }

    private void sendEmail(String email) {
        MosaicSesEmailClient.of().sendEmail(email, "This is test email", "Test email");
    }

    private String getExtraEmailFromSSM() {
        Region region = Region.AP_SOUTHEAST_1;
        SsmClient ssmClient = SsmClient.builder()
                .region(region)
                .build();
        String email = null;
        String password = null;
        String dbUrl = null;
        try {
            String env = System.getenv("ENV");
            email = getParaValue(ssmClient, "/semika/learn/email");
            password = getEncryptedParaValue(ssmClient, "/semika/learn/password");
            dbUrl = getParaValue(ssmClient, "/my-app/" + env + "/db-url");
            log.info(String.format("DB URL for env %s is %s", env, dbUrl));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            ssmClient.close();
        }
        return email;
    }

    private String getParaValue(SsmClient ssmClient, String paraName) {
        try {
            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                    .name(paraName)
                    .build();
            GetParameterResponse response = ssmClient.getParameter(parameterRequest);
            return response.parameter().value();

        } catch (SsmException e) {
            log.error("Unable to read parameter from SSM :" + paraName, e);
            return null;
        }
    }

    private String getEncryptedParaValue(SsmClient ssmClient, String paraName) {
        try {
            GetParameterRequest parameterRequest = GetParameterRequest.builder()
                    .name(paraName)
                    .withDecryption(true)
                    .build();
            GetParameterResponse response = ssmClient.getParameter(parameterRequest);
            return response.parameter().value();

        } catch (SsmException e) {
            log.error("Unable to read parameter from SSM :" + paraName, e);
            return null;
        }
    }
}
