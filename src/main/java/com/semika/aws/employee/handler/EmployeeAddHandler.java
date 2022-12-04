package com.semika.aws.employee.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.semika.aws.common.Operation;
import com.semika.aws.employee.model.EmployeeDto;
import com.semika.aws.employee.service.EmployeeService;
import com.semika.aws.model.response.ThaproResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  This is the entry point for the Lambda function
 */

public class EmployeeAddHandler implements RequestHandler<EmployeeDto, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeAddHandler.class);
    private EmployeeService employeeService;

    public EmployeeAddHandler() {
        this.employeeService = EmployeeService.of();
    }

    @Override
    public String handleRequest(EmployeeDto employeeDto, Context context) {
        LambdaLogger logger = context.getLogger();
        if (Operation.UP.equals(employeeDto.getOperation())) {
            LOGGER.info("Staring employee function");
            return "success";
        }

        logger.log(employeeDto.toString());

        employeeService.save(employeeDto, context);
        String jsonResponse = ThaproResponseBuilder
                .create()
                .withMessage("Employee saved successfully..")
                .withoutData()
                .toJson();

        logger.log("Employee saved successfully");

        return jsonResponse;
    }
}
