package com.semika.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 *  This is the entry point for the Lambda function
 */

public class Handler implements RequestHandler<Map<String,String>, String> {

    @Override
    public String handleRequest(Map<String, String> event, Context context) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        LambdaLogger logger = context.getLogger();
        ScanEmployees scanEmployees = new ScanEmployees();
        Boolean ans =  scanEmployees.sendEmployeMessage(context);

        String toEmail = event.get("email");
        logger.log("To email " + toEmail);

        String requestId = context.getAwsRequestId();
        logger.log("AWS Request ID = " + requestId);

        // log execution details
        logger.log("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
        logger.log("CONTEXT: " + gson.toJson(context));
        // process event
        logger.log("EVENT: " + gson.toJson(event));
        logger.log("EVENT TYPE: " + event.getClass().toString());

        if (ans) {
            logger.log("Messages sent: " + ans);
        }

        return "Mail not sent";
    }
}
