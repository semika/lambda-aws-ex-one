package com.semika.aws.employee.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.semika.aws.employee.model.EmployeeDto;
import com.semika.aws.employee.service.EmployeeService;
import com.semika.aws.model.response.GatewayProxyResponse;
import com.semika.aws.model.response.ThaproResponseBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 *  This is the entry point for the Lambda function
 */

public class EmployeeByIdHandler
        implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger LOGGER = LogManager.getLogger(EmployeeByIdHandler.class);
    private EmployeeService employeeService;

    public EmployeeByIdHandler() {
        this.employeeService = EmployeeService.of();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {

        Map<String, String> queryParam = apiGatewayProxyRequestEvent.getQueryStringParameters();

        String id = queryParam.get("id");

        LOGGER.info("Finding employee for id = " + id);

        EmployeeDto dto = employeeService.findById(id, context);

        String jsonResponse = ThaproResponseBuilder
                .<EmployeeDto>create()
                .withMessage("Found employee for ID " + id)
                .withData(dto)
                .toJson();

        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(jsonResponse);
        responseEvent.setStatusCode(200);
        return responseEvent;

    }
}
