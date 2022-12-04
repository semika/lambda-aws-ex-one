package com.semika.aws.employee.repository;

import com.amazonaws.services.lambda.runtime.Context;
import com.semika.aws.common.repository.BaseRepository;
import com.semika.aws.employee.model.Employee;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.*;

public class EmployeeRepository extends BaseRepository<Employee> {

    @Override
    public String get() {
        return "Employee";
    }

    @Override
    public Class<Employee> getEntityClass() {
        return Employee.class;
    }

    public static EmployeeRepository of() {
        return new EmployeeRepository();
    }

    public void save(Employee employee, Context context) {
        DynamoDbTable<Employee> table = getDbTable();
        table.putItem(employee);
    }

    public void update(Employee employee, Context context) {

    }

    public Employee findById(String id) {
        DynamoDbTable<Employee> table = getDbTable();
        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(id)
                .build());

        Iterator<Employee> results = table.query(queryConditional).items().iterator();
        Employee employee = results.next(); // Based on assumption, there can not be two employees with same id
        return employee;
    }

    public List<Employee> findAll() {
        List<Employee> employeeList = new ArrayList<>();
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.create();
        DynamoDbTable<Employee> mappedTable = enhancedClient.table(this.get(), TableSchema.fromBean(Employee.class));
        PageIterable<Employee> employeeIterable = mappedTable.scan();
        Iterator<Employee> employeeIterator = employeeIterable.items().iterator();
        employeeIterator.forEachRemaining((employee -> {
            employeeList.add(employee);
        }));
        return employeeList;
    }

    public List<Employee> findEmployeeByCriteria(String firstName) {

        DynamoDbTable<Employee> table = getDbTable();

        AttributeValue paramValue = AttributeValue.builder()
                .s("Lasanthi")
                .build();
        Map<String, AttributeValue> expressionValueMap = new HashMap<>();
        expressionValueMap.put(":val1", paramValue);

        Map<String, String> expressionNameMap = new HashMap<>();
        expressionNameMap.put("#first", "first");

        Expression expression = Expression.builder()
                .expressionValues(expressionValueMap)
                .expressionNames(expressionNameMap)
                .expression("#first = :val1")
                .build();

        ScanEnhancedRequest enhancedRequest = ScanEnhancedRequest.builder()
                .filterExpression(expression)
                .limit(15) // you can increase this value
                .build();

        PageIterable<Employee> employeePageIterable =  table.scan(enhancedRequest);
        Iterator<Employee> employeeIterator = table.scan(enhancedRequest).items().iterator();

        List<Employee> employeeList = new ArrayList<>();
        employeeIterator.forEachRemaining((Employee emp) -> employeeList.add(emp));
        return employeeList;
    }
}
