package com.chathu.covidapp.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.chathu.covidapp.R;
import com.chathu.covidapp.service.UserLastKnownLocationService;
import com.chathu.covidapp.service.UserLocationService;
import com.chathu.covidapp.util.SessionManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.JobIntentService;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

//        if (SessionManager.getInstance().getLoggedInUser() == null) {
//            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
//            MainActivity.this.startActivity(loginIntent);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize network library
        AndroidNetworking.initialize(getApplicationContext());

        if(SessionManager.getInstance().getLoggedInUser() == null){
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(loginIntent);
            return;
        }


        //Get permissions for location capture
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                1
        );

        //User last know location
        Intent mIntent = new Intent(this, UserLastKnownLocationService.class);
        JobIntentService.enqueueWork(this, UserLastKnownLocationService.class, 1,mIntent);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}