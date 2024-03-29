package uk.co.automatictester.cloudstart.backend.instances.patch;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;

import java.util.Map;
import java.util.Optional;

public class DataStore {

    private static final String TABLE_NAME = "CloudStartStore";
    private static final AmazonDynamoDB dynamo = AmazonDynamoDBClientBuilder.defaultClient();

    private DataStore() {
    }

    public static boolean hasCustomHostnameMapping(String key) {
        GetItemResult getItemResult = getItem(key);
        return getItemResult.getItem() != null;
    }

    public static Optional<String> getValueFromDataStore(String key) {
        GetItemResult getItemResult = getItem(key);
        Map<String, AttributeValue> item = getItemResult.getItem();
        if (item == null) {
            return Optional.empty();
        } else {
            return Optional.of(item.get("Value").getS());
        }
    }

    private static GetItemResult getItem(String key) {
        GetItemRequest getItemRequest = new GetItemRequest();
        getItemRequest.setTableName(TABLE_NAME);
        getItemRequest.addKeyEntry("Key", new AttributeValue(key));
        return dynamo.getItem(getItemRequest);
    }
}
