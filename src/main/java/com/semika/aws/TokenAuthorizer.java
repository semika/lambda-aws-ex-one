package com.semika.aws;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class TokenAuthorizer implements RequestHandler<TokenAuthorizerContext, AuthPolicy> {
    @Override
    public AuthPolicy handleRequest(TokenAuthorizerContext input, Context context) {

        LambdaLogger logger = context.getLogger();
        String token = input.getAuthorizationToken();
        String principalId = "xxxx";
        String methodArn = input.getMethodArn();
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

        // the example policy below denies access to all resources in the RestApi
        //return new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getDenyAllPolicy
        //        (region, awsAccountId, restApiId, stage));


        logger.log("Authorization Header : " + token + "************");
        logger.log("Method Arn : " + methodArn + "************");
        logger.log("Authorizer Type : " + input.getType() + "************");

        // the example policy below denies access to all resources in the RestApi
        return new AuthPolicy(principalId, PolicyDocument.getAllowAllPolicy
                (region, awsAccountId, restApiId, stage));
    }
}
