package com.om.demo.pollutionmap;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import com.loopj.android.http.*;
import com.om.demo.pollutionmap.model.AirPollutionData;
import com.om.demo.pollutionmap.network.AirPollutionRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private static final String TAG = "POLLUTION_MAP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.mapboxMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                // Customize map with markers, polylines, etc.
            }
        });
        AirPollutionRestClient.get("all/public/devices", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d(TAG,"JSON Object not desired");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Pull out the first event on the public timeline
                Log.d(TAG,"Success API Call");
                ArrayList<AirPollutionData> airPollutionDatas = parseAirPollutionData(response);
                Log.d(TAG,airPollutionDatas.get(0).getAqi().toString());

                // Do something with the response
            }
        });
    }

    private ArrayList<AirPollutionData> parseAirPollutionData(JSONArray response) {
        JSONArray firstJsonArray = null;
        ArrayList<AirPollutionData> datas = new ArrayList<>();
        try {
            firstJsonArray = (JSONArray) response.get(0);
            for (int i =0;i < firstJsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) firstJsonArray.get(i);
                Gson gson = new GsonBuilder().create();
                AirPollutionData deviceData = gson.fromJson(jsonObject.toString(),AirPollutionData.class);
                datas.add(deviceData);
            }


        } catch (JSONException e) {
            Log.e(TAG,e.getLocalizedMessage());
        }
       return datas;
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
