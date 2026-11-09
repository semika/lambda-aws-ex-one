package com.semika.aws.common.repository;

import com.semika.aws.employee.model.Employee;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    public T save(T t) {
        DynamoDbTable<T> table = getDbTable();
        table.putItem(t);
        return t;
    }

    public T update(T t) {
        DynamoDbTable<T> table = getDbTable();
        table.updateItem(t);
        return t;
    }

    public T findById(String id) {
        DynamoDbTable<T> table = getDbTable();
        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(id)
                .build());

        Iterator<T> results = table.query(queryConditional).items().iterator();
        T t = results.next(); // Based on assumption, there can not be two employees with same id
        return t;
    }

    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.create();
        DynamoDbTable<T> mappedTable = enhancedClient.table(this.get(), TableSchema.fromBean(getEntityClass()));
        PageIterable<T> iterable = mappedTable.scan();
        Iterator<T> iterator = iterable.items().iterator();
        iterator.forEachRemaining((t -> {
            list.add(t);
        }));
        return list;
    }
}
