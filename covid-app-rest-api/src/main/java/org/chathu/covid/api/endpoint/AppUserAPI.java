package org.chathu.covid.api.endpoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.azure.cosmos.CosmosException;
import com.azure.cosmos.implementation.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.chathu.covid.api.auth.AuthToken;
import org.chathu.covid.api.auth.SimpleAuthService;
import org.chathu.covid.api.controller.AppUserController;
import org.chathu.covid.api.model.documents.LocationHistory;
import org.chathu.covid.api.model.documents.User;
import org.json.JSONObject;

/**
 * Azure Functions with HTTP Trigger.
 */
public class AppUserAPI {

    @FunctionName("UserLogin")
    public HttpResponseMessage login(@HttpTrigger(name = "req",
            route = "user/login",
            methods = {HttpMethod.POST},
            authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
                                  final ExecutionContext context){

        if(request.getBody() == null){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("missing request body").build();
        }

        JSONObject body = new JSONObject(request.getBody());

        if(!body.has("username") || !body.has("password")){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("missing username/password").build();
        }

        String username = body.getString("username");
        String password = body.getString("password");

        SimpleAuthService authService = new SimpleAuthService();
        AuthToken authToken = authService.authenticateUser(username,password);

        if(authToken == null){
            return request.createResponseBuilder(HttpStatus.UNAUTHORIZED).build();
        }

        JSONObject responseJson = new JSONObject();
        responseJson.put("authToken", authToken.getUserToken());

        return request.createResponseBuilder(HttpStatus.OK).body(responseJson.toString()).build();
    }

    @FunctionName("UserRegistration")
    public HttpResponseMessage register(
            @HttpTrigger(name = "req",
                    route = "user/register",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
                                        final ExecutionContext context){

        if(request.getBody() == null){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("missing request body").build();
        }

        JSONObject body = new JSONObject(request.getBody());

        if(!body.has("username") || !body.has("password")){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("missing username/password").build();
        }

        String userName = body.getString("username");
        String password = body.getString("password");
        String passHash = new SimpleAuthService().getPassHash(password);

        String firstName = body.has("firstname") ? body.getString("firstname") : null;
        String lastName = body.has("lastname") ? body.getString("lastname") : null;
        String phoneNumber = body.has("phonenumber") ? body.getString("phonenumber") : null;
        String nic = body.has("nic") ? body.getString("nic") : null;
        String dob = body.has("dob") ? body.getString("dob") : null;


        User userDoc = new User();
        userDoc.setId(userName);
        userDoc.setFirstName(firstName);
        userDoc.setLastName(lastName);
        userDoc.setPassHash(passHash);
        userDoc.setPhoneNumber(phoneNumber);
        userDoc.setPhoneNumber(nic);
        userDoc.setPhoneNumber(dob);
        userDoc.setUserType(User.UserType.STANDARD);


        AppUserController userController = new AppUserController();
        try{
            userController.registerUser(userDoc);
            return request.createResponseBuilder(HttpStatus.OK).build();
        }catch (CosmosException ex){
            //ex.printStackTrace();
            return request.createResponseBuilder(HttpStatus.FORBIDDEN).build();
        }
    }


    @FunctionName("UserExists")
    public HttpResponseMessage userExists(@HttpTrigger(name = "req",
            route = "user/exists",
            methods = {HttpMethod.POST},
            authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
                                     final ExecutionContext context){

        if(request.getBody() == null){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("missing request body").build();
        }

        JSONObject body = new JSONObject(request.getBody());
        AuthToken token = new SimpleAuthService().authenticateRequest(body);

        if(token == null){
            return request.createResponseBuilder(HttpStatus.UNAUTHORIZED).build();
        }

        if(!body.has("username")){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("missing username").build();
        }

        String username = body.getString("username");

        AppUserController controller = new AppUserController();
        User user = controller.getUser(username);

        JSONObject response = new JSONObject();
        response.put("userId", user.getId());

        return request.createResponseBuilder(HttpStatus.OK).body(response.toString()).build();
    }


    @FunctionName("LocationHistory")
    public HttpResponseMessage locationHistory(
            @HttpTrigger(name = "req",
                    route = "user/location/history",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<String> request,
            final ExecutionContext context){

        if(request.getBody() == null){
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("missing request body").build();
        }

        JSONObject body = new JSONObject(request.getBody());
        AuthToken token = new SimpleAuthService().authenticateRequest(body);

        if(token == null){
            return request.createResponseBuilder(HttpStatus.UNAUTHORIZED).build();
        }

        if(body.has("fromDate") && body.has("toDate")){
            String fromDate = body.getString("fromDate");
            String toDate = body.getString("toDate");

            Date fromDateConverted;
            Date toDateConvereted;
            try {
                fromDateConverted = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
                toDateConvereted = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
            } catch (ParseException e) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("wrong date formats").build();
            }

            AppUserController controller = new AppUserController();
            List<LocationHistory> locHistory = controller.getUserLocationHistory(token.getUserName(),fromDateConverted,toDateConvereted);

            ObjectMapper mapper = Utils.getSimpleObjectMapper();
            JsonNode node = mapper.valueToTree(locHistory);

            return request.createResponseBuilder(HttpStatus.OK).body(node.toString()).build();

        }else{
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("missing from/to dates").build();
        }
    }

}
