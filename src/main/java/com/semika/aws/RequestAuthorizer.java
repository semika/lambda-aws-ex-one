package com.semika.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequestAuthorizer implements RequestHandler<Map<String, Object>, AuthPolicy> {

    @Override
    public AuthPolicy handleRequest(Map<String, Object> event, Context context) {

        LambdaLogger logger = context.getLogger();
        logger.log(event.toString());
        String token = event.toString();
        String principalId = "xxxx";
        String type = (String)event.get("type");
        String methodArn = (String)event.get("methodArn");;
        String[] arnPartials = methodArn.split(":");
        String region = arnPartials[3];
        String awsAccountId = arnPartials[4];
        String[] apiGatewayArnPartials = arnPartials[5].split("/");
        String restApiId = apiGatewayArnPartials[0];
        String stage = apiGatewayArnPartials[1];
        String httpMethod = apiGatewayArnPartials[2];
        String resource = ""; // root resource
        if (apiGatewayArnPartials.length == 4) {
            resource = apiGatewayArnPartials[3];
        }

        Map<String,String> headerMap = (Map<String, String>)event.get("headers");


//        Map<String, Object> response = new HashMap<String, Object>();
//        response.put("principalId", "1234");
//
//        Map<String, Object> policyDocument = new HashMap<String, Object>();
//        policyDocument.put("Version", "2012-10-17");
//
//        Map<String, String> statement = new HashMap<>();
//        statement.put("Action", "execute-api:Invoke");
//        statement.put("Effect", "Allow");
//        //statement.put("Resource", (String) event.get("methodArn"));
//
//        statement.put("Resource", "arn:aws:execute-api:ap-southeast-1:762002331286:b6h4b7wtnd/*/POST/send-single-mail");
//
//
//        policyDocument.put("Statement", Arrays.asList(statement));

//        response.put("policyDocument", policyDocument);
//
//        Map<String, String> ctx = new HashMap<String, String>();
//        ctx.put("now", new Date().toString());
//        response.put("context", ctx);
//
//        return null;
        logger.log("Client ID : " + headerMap.get("client_id") + "************");
        logger.log("Client Secrent : " + headerMap.get("client_secret") + "************");
        logger.log("Authorizer Type : " + type + "************");

        // the example policy below denies access to all resources in the RestApi
        return new AuthPolicy(principalId, PolicyDocument.getAllowAllPolicy
                (region, awsAccountId, restApiId, stage));
    }
}
