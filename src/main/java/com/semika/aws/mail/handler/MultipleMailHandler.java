package com.semika.aws.mail.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.semika.aws.model.request.MultipleMailRequest;

public class MultipleMailHandler implements RequestHandler<MultipleMailRequest, String> {
    @Override
    public String handleRequest(MultipleMailRequest multipleMailRequest, Context context) {

        LambdaLogger logger = context.getLogger();

        multipleMailRequest.getEmailList().forEach(email -> {
            logger.log("Email is = " + email);
        });
        return "success";
    }
}
