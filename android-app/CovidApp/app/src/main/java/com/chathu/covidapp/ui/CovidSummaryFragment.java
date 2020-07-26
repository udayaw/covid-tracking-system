package com.chathu.covidapp.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chathu.covidapp.R;
import com.chathu.covidapp.api.APICallBack;
import com.chathu.covidapp.api.APIResponse;
import com.chathu.covidapp.api.RestAPIClient;
import com.chathu.covidapp.model.CovidLocation;
import com.chathu.covidapp.model.CovidSummary;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CovidSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CovidSummaryFragment extends Fragment implements OnMapReadyCallback {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private static final String MAP_VIEW_BUNDLE_KEY = "a24jk23h4j23";
    private MapView mapView;
    private GoogleMap gmap;

    public CovidSummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CovidSummaryFragment.
     */
    public static CovidSummaryFragment newInstance(String param1, String param2) {
        CovidSummaryFragment fragment = new CovidSummaryFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }





    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //Bundle mapViewBundle = null;
//        if (savedInstanceState != null) {
//            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
//        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_covid_summary, container, false);

        //initialize google maps
        mapView = view.findViewById(R.id.covidMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        //load covid summary figures
        new RestAPIClient().getCovidSummary(new APICallBack() {
            @Override
            public void onComplete(APIResponse response) {

                if(response.getStatus() == APIResponse.APIResponseStatus.SUCCESS){
                    CovidSummary summary = (CovidSummary)response.getBody();
                    TextView txtConfirmed = view.findViewById(R.id.txtConfirmed);
                    TextView txtRecovered = view.findViewById(R.id.txtRecovered);
                    TextView txtDeaths = view.findViewById(R.id.txtDeaths);

                    txtConfirmed.setText(summary.getConfirmed() + "");
                    txtRecovered.setText(summary.getRecovered() + "");
                    txtDeaths.setText(summary.getDeaths() + "");


                }
            }
        });

        return view;
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {

        gmap = googleMap;
        gmap.setMinZoomPreference(10.0f);
        LatLng ny = new LatLng(6.914669, 79.962039);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));


        new RestAPIClient().getCovidLocations(new APICallBack() {
            @Override
            public void onComplete(APIResponse response) {

                if(response.getStatus() == APIResponse.APIResponseStatus.SUCCESS){

                    List<CovidLocation> covidLocations = (List<CovidLocation>)response.getBody();
                    for(CovidLocation loc : covidLocations){
                        gmap.addCircle(new CircleOptions()
                                .center(new LatLng(loc.getLatitude(), loc.getLongitude()))
                                .strokeColor(Color.RED)
                                .fillColor(0x33CC0000)
                                .strokeWidth(1)
                                .radius(loc.getCovidCases() * 1000)
                        );
                    }
                }
            }
        });



    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}