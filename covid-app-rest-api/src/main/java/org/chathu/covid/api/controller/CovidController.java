package org.chathu.covid.api.controller;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.CosmosException;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.util.CosmosPagedIterable;
import org.chathu.covid.api.cosmos.DBConstants;
import org.chathu.covid.api.cosmos.DBManager;
import org.chathu.covid.api.model.documents.CovidLocation;
import org.chathu.covid.api.model.documents.CovidSummary;
import org.chathu.covid.api.model.documents.LocationHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CovidController {
    public List<CovidLocation> getCovidLocations() throws CosmosException {
        ArrayList<CovidLocation> covidLocations = new ArrayList<CovidLocation>();

        DBManager manager = DBManager.getDBManager();
        CosmosDatabase db = manager.getDefaultDatabase();
        CosmosContainer container = db.getContainer(DBConstants.COVID_LOCATION_CONTAINER);
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();

        CosmosPagedIterable<CovidLocation> listItems = container.queryItems("SELECT * from l" ,options, CovidLocation.class);

        listItems.forEach(doc->covidLocations.add(doc));

        return covidLocations;
    }

    public CovidSummary getCovidSummary() throws CosmosException {

        DBManager manager = DBManager.getDBManager();
        CosmosDatabase db = manager.getDefaultDatabase();
        CosmosContainer container = db.getContainer(DBConstants.COVID_SUMMARY_CONTAINER);
        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();

        CosmosPagedIterable<CovidSummary> listItems = container.queryItems("SELECT * from l" ,options, CovidSummary.class);

        CovidSummary s = listItems.iterator().next();

        return s;
    }
}
