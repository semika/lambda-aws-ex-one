package com.semika.aws.common.repository;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.function.Supplier;

public abstract class BaseRepository <T> implements Supplier<String> {

    public abstract Class<T> getEntityClass();

    public DynamoDbEnhancedClient getClient() {
        DynamoDbEnhancedClient client = DynamoDbEnhancedClient.create();
        return client;
    }

    public DynamoDbTable<T> getDbTable() {
        DynamoDbTable<T> table = getClient().table(get(), TableSchema.fromBean(getEntityClass()));
        return table;
    }

    public void _getClient() {
        Region region = Region.AP_SOUTHEAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();
    }
}
