package com.chathu.covidapp.service;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.JobIntentService;

import com.chathu.covidapp.util.SessionManager;
import com.microsoft.azure.eventhubs.ConnectionStringBuilder;
import com.microsoft.azure.eventhubs.EventData;
import com.microsoft.azure.eventhubs.EventHubClient;
import com.microsoft.azure.eventhubs.EventHubException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

public class UserLastKnownLocationService extends JobIntentService {

    private LocationManager locationMgr;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        //init EventHubs

        ConnectionStringBuilder connStr = new ConnectionStringBuilder()
        .setNamespaceName("covid-app-location-stream")
        .setEventHubName("location-stream")
        .setSasKeyName("RootManageSharedAccessKey")
        .setSasKey("+1CGEnocSs5ZLZS32GZj7oXpCZZ20gjrCsHeNGE1ucc=");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        EventHubClient ehClient = null;
        try {
            ehClient = EventHubClient.createSync(connStr.toString(), executorService);
        } catch (EventHubException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        while(true){
            try {
                Thread.sleep(5000);
                Location loc = locationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                Log.w("Loc Received","..............");
                //Toast.makeText(getBaseContext(),loc.getLatitude() + "" + loc.getLongitude(),Toast.LENGTH_SHORT);



                EventData sendEvent = EventData.create(
                        new JSONObject()
                        .put("user", SessionManager.getInstance().getLoggedInUser().getUserId())
                        .put("lat",loc.getLatitude())
                        .put("lon",loc.getLongitude())
                                .put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                                .toString().getBytes()
                );
                ehClient.sendSync(sendEvent);

                toast(loc.getLatitude() + "/" + loc.getLongitude() + " pushed");

            } catch (InterruptedException | EventHubException | JSONException e) {
                e.printStackTrace();
                toast(" push failed");
            }
        }
    }

    final Handler mHandler = new Handler();

    // Helper for showing tests
    void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override public void run() {
                Toast.makeText(UserLastKnownLocationService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void sendToEventHub(Location loc){
//        ConnectionStringBuilder connStr = new ConnectionStringBuilder()
//        .setNamespaceName("covid-app-location-stream")
//        .setEventHubName("location-stream")
//        .setSasKeyName("RootManageSharedAccessKey")
//        .setSasKey("+1CGEnocSs5ZLZS32GZj7oXpCZZ20gjrCsHeNGE1ucc=");
//
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//        EventHubClient ehClient = null;
//        try {
//            ehClient = EventHubClient.createSync(connStr.toString(), executorService);
//            for (int i = 0; i < 10; i++) {
//
//                EventData sendEvent = EventData.create( j1.toString().getBytes());
//
//                // Send - not tied to any partition
//                // Event Hubs service will round-robin the events across all Event Hubs partitions.
//                // This is the recommended & most reliable way to send to Event Hubs.
//                ehClient.sendSync(sendEvent);
//            }
//        } catch (EventHubException|IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                ehClient.closeSync();
//            } catch (EventHubException e) {
//                e.printStackTrace();
//            }
//            executorService.shutdown();
//        }
//    }
}
