package com.semika.aws.employee.model;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Getter(AccessLevel.NONE)
    private String id;

    @Getter(AccessLevel.NONE)
    private String first;
    private String phone;
    private String startDate;

    private String email;

    @DynamoDbPartitionKey
    public String getId() {
        return this.id;
    }

    @DynamoDbSortKey
    public String getFirst() {
        return this.first;
    }

}
