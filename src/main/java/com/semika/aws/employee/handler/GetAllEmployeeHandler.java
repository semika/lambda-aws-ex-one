package com.semika.aws.employee.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.semika.aws.employee.model.EmployeeDto;
import com.semika.aws.employee.service.EmployeeService;

import java.util.List;

public class GetAllEmployeeHandler implements RequestHandler<Object, List<EmployeeDto>> {

    private EmployeeService employeeService;

    public GetAllEmployeeHandler() {
        this.employeeService = EmployeeService.of();
    }

    @Override
    public List<EmployeeDto> handleRequest(Object object, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Getting all employees list ..");
        List<EmployeeDto> employeeDtoList = employeeService.findAll();
        return employeeDtoList;
    }
}
