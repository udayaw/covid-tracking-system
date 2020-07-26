package com.chathu.covidapp.api;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.chathu.covidapp.model.CovidLocation;
import com.chathu.covidapp.model.CovidSummary;
import com.chathu.covidapp.model.LoggedInUser;
import com.chathu.covidapp.model.User;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Response;

public class RestAPIClient {

    private final String APP_TOKEN = "SAXFFy5CWN6d3jiRtlHMgel2xXuAqwIFatsgLnQvCkYZBism8FuMjg==";

    public void registerUser(final User newUser, final APICallBack callBack) throws JSONException {



        AndroidNetworking
                .post("https://covid-app-rest-api.azurewebsites.net/api/user/register?code=" + APP_TOKEN)
                .addJSONObjectBody(new JSONObject()
                        .put("username",newUser.getUserName())
                        .put("password",newUser.getPassword())
                        .put("firstname",newUser.getFirstName())
                        .put("lastname",newUser.getLastName())
                        .put("phonenumber",newUser.getPhoneNumber())
                        .put("nic",newUser.getNIC())
                        .put("dob",newUser.getDOB())
                )
                .build().getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
//                        Log.w("onResponse","SUceess");
                        callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.SUCCESS, null));
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.w("onResponse",anError.getErrorDetail());
                        callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
                    }
                });
    }

    public void tryLogin(final String userName, String password, final APICallBack callBack) throws JSONException {

        AndroidNetworking
                .post("https://covid-app-rest-api.azurewebsites.net/api/user/login?code=" + APP_TOKEN)
                .addJSONObjectBody(new JSONObject()
                        .put("username",userName)
                        .put("password",password))
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                if(response != null && response.has("authToken")){
                    try {
                        String authToken = response.get("authToken").toString();
                        LoggedInUser user = new LoggedInUser(userName,authToken);
                        callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.SUCCESS, user));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
                    }
                }
                callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
            }

            @Override
            public void onError(ANError anError) {
                callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
            }
        });

    }

    public void getCovidLocations(final APICallBack callBack){

//        ArrayList<CovidLocation> covidLocationList = new ArrayList<>();
//        covidLocationList.add(new CovidLocation(6.704242, 80.016261,1));
//        covidLocationList.add(new CovidLocation(7.066506, 80.049535,3));
//        covidLocationList.add(new CovidLocation(6.914669, 79.962039,7));

        //callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.SUCCESS, covidLocationList));

        AndroidNetworking
                .get("https://covid-app-rest-api.azurewebsites.net/api/covid/locations")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                if(response != null){
//
//                    ArrayList<CovidLocation> covidLocationList = new ArrayList<>();
//                    covidLocationList.add(new CovidLocation(6.704242, 80.016261,1));
//                    covidLocationList.add(new CovidLocation(7.066506, 80.049535,3));
//                    covidLocationList.add(new CovidLocation(6.914669, 79.962039,7));

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        List<CovidLocation> covidLocationList = mapper.readValue(response.toString(),new TypeReference<List<CovidLocation>>() {});
                        callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.SUCCESS, covidLocationList));
                    } catch (IOException e) {
                        e.printStackTrace();
                        callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
                    }

                }
                callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
            }

            @Override
            public void onError(ANError anError) {
                callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
            }
        });

    }


    public void getCovidSummary(final APICallBack callBack){
        CovidSummary summary = new CovidSummary();
        summary.setConfirmed(101);
        summary.setRecovered(97);
        summary.setDeaths(3);

        AndroidNetworking
                .get("https://covid-app-rest-api.azurewebsites.net/api/covid/summary")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {

                if(response != null){

                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        CovidSummary covidSummary = mapper.readValue(response.toString(),CovidSummary.class);
                        callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.SUCCESS, covidSummary));
                    } catch (IOException e) {
                        e.printStackTrace();
                        callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
                    }

                }
                callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
            }

            @Override
            public void onError(ANError anError) {
                callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
            }
        });
    }




}
