package com.semika.aws;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Employee {

    private String Id;
    private String first;
    private String phone;
    private String startDate;

    private String email;

    public void setId(String id) {
        this.Id = id;
    }

    @DynamoDbPartitionKey
    public String getId() {
        return this.Id;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    @DynamoDbSortKey
    public String getFirst() {
        return this.first;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
