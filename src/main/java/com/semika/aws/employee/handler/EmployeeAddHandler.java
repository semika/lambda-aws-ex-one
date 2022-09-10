package com.semika.aws.employee.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.semika.aws.employee.model.EmployeeDto;
import com.semika.aws.employee.service.EmployeeService;
import com.semika.aws.model.response.ThaproResponseBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *  This is the entry point for the Lambda function
 */

public class EmployeeAddHandler implements RequestHandler<EmployeeDto, String> {

    static final Logger LOGGER = LogManager.getLogger(EmployeeAddHandler.class);
    private EmployeeService employeeService;

    public EmployeeAddHandler() {
        this.employeeService = EmployeeService.of();
    }

    @Override
    public String handleRequest(EmployeeDto employeeDto, Context context) {
        LambdaLogger logger = context.getLogger();
        employeeService.save(employeeDto, context);
        String jsonResponse = ThaproResponseBuilder
                .create()
                .withMessage("Employee saved successfully..")
                .withoutData()
                .toJson();

        LOGGER.info("Employee saved successfully");

        return jsonResponse;
    }
}
