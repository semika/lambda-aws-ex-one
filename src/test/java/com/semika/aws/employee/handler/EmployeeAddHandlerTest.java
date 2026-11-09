package com.semika.aws.employee.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.semika.aws.employee.model.EmployeeDto;
import com.semika.aws.employee.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeAddHandlerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private Context context;

    @Mock
    private LambdaLogger lambdaLogger;

    private MockedStatic<EmployeeService> employeeServiceStatic;

    private EmployeeAddHandler handler;

    @BeforeEach
    void setUp() {
        employeeServiceStatic = mockStatic(EmployeeService.class);
        employeeServiceStatic.when(EmployeeService::of).thenReturn(employeeService);
        when(context.getLogger()).thenReturn(lambdaLogger);

        handler = new EmployeeAddHandler();
    }

    @AfterEach
    void tearDown() {
        employeeServiceStatic.close();
    }

    @Test
    void handleRequest_savesNewEmployee_whenIdIsNull() {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .first("Jane")
                .email("jane@example.com")
                .build();

        String response = handler.handleRequest(employeeDto, context);

        verify(employeeService).save(employeeDto);
        verify(employeeService, never()).update(any());
        assertTrue(response.contains("Employee saved successfully.."));
    }

    @Test
    void handleRequest_updatesExistingEmployee_whenIdIsPresent() {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id("emp-1")
                .first("Jane")
                .email("jane@example.com")
                .build();

        String response = handler.handleRequest(employeeDto, context);

        verify(employeeService).update(employeeDto);
        verify(employeeService, never()).save(any());
        assertTrue(response.contains("Employee saved successfully.."));
    }

    @Test
    void handleRequest_logsEmployeeDetailsAndConfirmation() {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .first("Jane")
                .build();

        handler.handleRequest(employeeDto, context);

        verify(lambdaLogger).log(employeeDto.toString());
        verify(lambdaLogger).log("Employee saved successfully");
    }
}
