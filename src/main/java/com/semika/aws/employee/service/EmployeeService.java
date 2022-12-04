package com.semika.aws.employee.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.semika.aws.employee.model.Employee;
import com.semika.aws.employee.model.EmployeeDto;
import com.semika.aws.employee.repository.EmployeeRepository;
import com.semika.aws.mail.client.MosaicSesEmailClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.util.List;

public class EmployeeService {

    private EmployeeConverter employeeConverter;
    private EmployeeRepository employeeRepository;

    public EmployeeService() {
        this.employeeConverter = new EmployeeConverter();
        this.employeeRepository = new EmployeeRepository();
    }

    public static EmployeeService of() {
        return new EmployeeService();
    }

    public void save(EmployeeDto employeeDto, Context context) {
        Employee employee = employeeConverter.dtoToDomain(employeeDto);
        employeeRepository.save(employee, context);
        //sendNotification(employeeDto.getEmail(), context);
    }

    public void update(EmployeeDto dto, Context context) {
        Employee employee = employeeRepository.findById(dto.getId());
        employeeConverter.dtoToDomain(dto, employee);
        employeeRepository.update(employee, context);
        //sendNotification(employeeDto.getEmail(), context);
    }

    public EmployeeDto findById(String id, Context context) {
        Employee employee = employeeRepository.findById(id);
        return employeeConverter.domainToDto(employee);
    }

    public List<EmployeeDto> findAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeConverter.toList(employeeList);
    }

    private void sendNotification(String email, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Sending verification email");
        sendEmail(email, context);
    }

    private void sendEmail(String firstName, Context context, String toEmail) {
        LambdaLogger logger = context.getLogger();
        //List<Employee> empList = EmployeeRepository.of().findEmployeeByFirstName(firstName);
        //empList.forEach((Employee emp) -> sendEmail(emp.getEmail(), context));
        sendEmail(toEmail, context);
        logger.log("All mails sent successfully...");
    }

    private void sendEmail(String email, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Sending email to :" + email);
        MosaicSesEmailClient.of().sendEmail(email, "This is test email", "Test email");
    }

    private void sentTextMessage(String first, String phone) {

        SnsClient snsClient = SnsClient.builder()
                .region(Region.US_WEST_2)
                .build();
        String message = first +" happy one year anniversary. We are very happy that you have been working here for a year! ";

        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .phoneNumber(phone)
                    .build();

            snsClient.publish(request);
        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
