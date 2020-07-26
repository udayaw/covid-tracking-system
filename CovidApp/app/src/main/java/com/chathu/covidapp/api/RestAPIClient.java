package com.chathu.covidapp.api;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.chathu.covidapp.model.CovidLocation;
import com.chathu.covidapp.model.CovidSummary;
import com.chathu.covidapp.model.LoggedInUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestAPIClient {

    private final String APP_TOKEN = "SAXFFy5CWN6d3jiRtlHMgel2xXuAqwIFatsgLnQvCkYZBism8FuMjg==";

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

        ArrayList<CovidLocation> covidLocationList = new ArrayList<>();
        covidLocationList.add(new CovidLocation(6.704242, 80.016261,1));
        covidLocationList.add(new CovidLocation(7.066506, 80.049535,3));
        covidLocationList.add(new CovidLocation(6.914669, 79.962039,7));

        callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.SUCCESS, covidLocationList));

//        AndroidNetworking
//                .get("https://covid-app-rest-api.azurewebsites.net/api/covid/summary")
//                .build().getAsJSONObject(new JSONObjectRequestListener() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                if(response != null){
//
//                    ArrayList<CovidLocation> covidLocationList = new ArrayList<>();
//                    covidLocationList.add(new CovidLocation(6.704242, 80.016261,1));
//                    covidLocationList.add(new CovidLocation(7.066506, 80.049535,3));
//                    covidLocationList.add(new CovidLocation(6.914669, 79.962039,7));
//
//                    callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.SUCCESS, covidLocationList));
//                }
//                callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
//            }
//
//            @Override
//            public void onError(ANError anError) {
//                callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
//            }
//        });




//        AndroidNetworking
//                .get("https://covid-app-rest-api.azurewebsites.net/api/covid/summary")
//                .build().getAsJSONObject(new JSONObjectRequestListener() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                if(response != null){
//
//                    ArrayList<CovidLocation> covidLocationList = new ArrayList<>();
//                    covidLocationList.add(new CovidLocation(6.704242, 80.016261,1));
//                    covidLocationList.add(new CovidLocation(7.066506, 80.049535,3));
//                    covidLocationList.add(new CovidLocation(6.914669, 79.962039,7));
//
//                    callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.SUCCESS, covidLocationList));
//                }
//                callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
//            }
//
//            @Override
//            public void onError(ANError anError) {
//                callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.FAIL, null));
//            }
//        });

    }


    public void getCovidSummary(final APICallBack callBack){
        CovidSummary summary = new CovidSummary();
        summary.setConfirmed(101);
        summary.setRecovered(97);
        summary.setDeaths(3);

        callBack.onComplete(new APIResponse(APIResponse.APIResponseStatus.SUCCESS, summary));
    }

}
