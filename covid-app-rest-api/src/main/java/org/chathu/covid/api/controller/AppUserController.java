package org.chathu.covid.api.controller;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.implementation.NotFoundException;
import com.azure.cosmos.implementation.Utils;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.chathu.covid.api.cosmos.DBConstants;
import org.chathu.covid.api.cosmos.DBManager;
import org.chathu.covid.api.model.documents.LocationHistory;
import org.chathu.covid.api.model.documents.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppUserController {



    public AppUserController(){

    }

    public void registerUser(User doc) throws CosmosException {
        DBManager manager = DBManager.getDBManager();
        CosmosDatabase db = manager.getDefaultDatabase();
        CosmosContainer container = db.getContainer(DBConstants.USER_CONTAINER);

        ObjectMapper mapper = Utils.getSimpleObjectMapper();
        JsonNode node = mapper.valueToTree(doc);

        container.createItem(node);
    }

    public User getUser(String id) throws CosmosException {
        DBManager manager = DBManager.getDBManager();
        CosmosDatabase db = manager.getDefaultDatabase();
        CosmosContainer container = db.getContainer(DBConstants.USER_CONTAINER);
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();

        try {
            User userDoc = container.readItem(id, new PartitionKey(id), User.class).getItem();
            return userDoc;
        }catch (NotFoundException e){
            e.printStackTrace();
        }

        return null;
    }

    public List<LocationHistory> getUserLocationHistory(String userId, Date from, Date to) throws CosmosException{
        ArrayList<LocationHistory> locHistory = new ArrayList<LocationHistory>();

        DBManager manager = DBManager.getDBManager();
        CosmosDatabase db = manager.getDefaultDatabase();
        CosmosContainer container = db.getContainer(DBConstants.LOCATION_HISTORY_CONTAINER);
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();

        String cosmosFromDate = new SimpleDateFormat("yyyy-MM-dd").format(from);
        String cosmosToDate = new SimpleDateFormat("yyyy-MM-dd").format(to);

        CosmosPagedIterable<LocationHistory> listItems = container.queryItems("SELECT * from l WHERE l.userId = '"+userId+"' and l.timestamp >= '"+cosmosFromDate +"' and l.timestamp <='" + cosmosToDate + "'" ,options, LocationHistory.class);

        listItems.forEach(doc->locHistory.add(doc));

        return locHistory;
    }
}
