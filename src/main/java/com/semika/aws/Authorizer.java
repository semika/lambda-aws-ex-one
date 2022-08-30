package com.semika.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.semika.aws.auth.PolicyDocument;
import com.semika.aws.auth.Response;
import com.semika.aws.auth.Statement;
import com.semika.aws.util.JWTUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Authorizer implements RequestHandler<APIGatewayProxyRequestEvent, Response> {

    @Override
    public Response handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        LambdaLogger logger = context.getLogger();

        Map<String, String> headers = event.getHeaders();
        //String authorizationToken = headers.get("Authorization");
        String auth = "Deny";
        //String sub = JWTUtil.getSub(authorizationToken);

        String sub = "7a53067e-8beb-4ac8-822c-369fe4a36a46";
        if (sub != null) {
            auth = "Allow";
        }

        Map<String, String> ctx = new HashMap<String, String>();
        ctx.put("sub", sub);

        if (event == null) {
            logger.log("Event is null...............");
        }

        APIGatewayProxyRequestEvent.ProxyRequestContext proxyContext = event.getRequestContext();

        if (proxyContext == null) {
            logger.log("proxyContext is null...............");
        }

        APIGatewayProxyRequestEvent.RequestIdentity identity = proxyContext.getIdentity();

        String arn = String.format("arn:aws:execute-api:%s:%s:%s/%s/%s/%s",
                System.getenv("AWS_REGION"), proxyContext.getAccountId(),
                proxyContext.getApiId(), proxyContext.getStage(), proxyContext.getHttpMethod(), "*");
        logger.log("********ARN = " + arn);

        Statement statement = Statement.builder().effect(auth).resource(arn).build();
        PolicyDocument policyDocument = PolicyDocument.builder().statements(Collections.singletonList(statement))
                .build();
        return Response.builder()
                .principalId(identity.getAccountId())
                .policyDocument(policyDocument)
                .context(ctx).build();
    }
}
