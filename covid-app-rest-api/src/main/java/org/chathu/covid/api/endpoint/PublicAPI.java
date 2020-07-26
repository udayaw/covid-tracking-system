package org.chathu.covid.api.endpoint;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.chathu.covid.api.auth.AuthToken;
import org.chathu.covid.api.auth.SimpleAuthService;
import org.json.JSONObject;

public class PublicAPI {

    @FunctionName("CovidSummary")
    public HttpResponseMessage covidSummary(
            @HttpTrigger(name = "req",
                    route = "covid/summary",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<String> request,
            final ExecutionContext context){

        //accepts date range
        if(request.getQueryParameters().containsKey("dt")){

        }

        return request.createResponseBuilder(HttpStatus.OK).body("Here is your body").build();
    }

    @FunctionName("CovidLocation")
    public HttpResponseMessage covidLocations(
            @HttpTrigger(name = "req",
                    route = "covid/locations",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<String> request,
            final ExecutionContext context){

        //accepts date range
        if(request.getQueryParameters().containsKey("covidId")){

        }

        return request.createResponseBuilder(HttpStatus.OK).body("Here is your body").build();
    }


}
