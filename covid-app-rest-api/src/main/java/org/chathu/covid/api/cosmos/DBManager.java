package org.chathu.covid.api.cosmos;


import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.implementation.ConnectionPolicy;

import java.io.Closeable;
import java.io.IOException;

public class DBManager{
    private final String HOST = "https://comos-covid-app.documents.azure.com:443/";
    private final String MASTER_KEY = "8hQ7LPlTL5IhRSKheKyLolBTrtLNjbWzFt0F3zkhvYqEOY7ok1YJ9Qz9Ic7xhRbjoxJ9jZ4xQJFM0FzbdOdS0w==";

    private CosmosClient cosmosClient;

    private static DBManager manager;

    private DBManager(){
        cosmosClient = new CosmosClientBuilder()
                .endpoint(HOST)
                .key(MASTER_KEY)
                .consistencyLevel(ConsistencyLevel.SESSION)
                .contentResponseOnWriteEnabled(true)
                .buildClient();
    }

    public static DBManager getDBManager(){
        if(manager == null){
            manager = new DBManager();
        }
        return manager;
    }

    public CosmosDatabase getDefaultDatabase(){
        return cosmosClient.getDatabase(DBConstants.DATABASE_ID);
    }

    public CosmosClient getCosmosClient() {
        return cosmosClient;
    }
}