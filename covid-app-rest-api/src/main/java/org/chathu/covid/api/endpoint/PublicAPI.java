package org.chathu.covid.api.endpoint;

import com.azure.cosmos.implementation.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.chathu.covid.api.auth.AuthToken;
import org.chathu.covid.api.auth.SimpleAuthService;
import org.chathu.covid.api.controller.AppUserController;
import org.chathu.covid.api.controller.CovidController;
import org.chathu.covid.api.model.documents.CovidLocation;
import org.chathu.covid.api.model.documents.CovidSummary;
import org.chathu.covid.api.model.documents.LocationHistory;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PublicAPI {

    @FunctionName("CovidSummary")
    public HttpResponseMessage covidSummary(
            @HttpTrigger(name = "req",
                    route = "covid/summary",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<String> request,
            final ExecutionContext context){

        CovidController controller = new CovidController();
        CovidSummary summary = controller.getCovidSummary();
        ObjectMapper mapper = Utils.getSimpleObjectMapper();
        JsonNode node = mapper.valueToTree(summary);

        return request.createResponseBuilder(HttpStatus.OK).body(node.toString()).build();
    }

    @FunctionName("CovidLocation")
    public HttpResponseMessage covidLocations(
            @HttpTrigger(name = "req",
                    route = "covid/locations",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<String> request,
            final ExecutionContext context){

            CovidController controller = new CovidController();
            List<CovidLocation> covidLocations = controller.getCovidLocations();
            ObjectMapper mapper = Utils.getSimpleObjectMapper();
            JsonNode node = mapper.valueToTree(covidLocations);

            return request.createResponseBuilder(HttpStatus.OK).body(node.toString()).build();

    }


}
