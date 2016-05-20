package com.om.demo.pollutionmap;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.mapboxMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final MapboxMap mapboxMap) {

                // Customize map with markers, polylines, etc.
                AirPollutionRestClient.get("all/public/devices", null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray
                        Log.d(TAG, "JSON Object not desired");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        // Pull out the first event on the public timeline
                        Log.d(TAG, "Success API Call");
                        ArrayList<AirPollutionData> airPollutionDatas = parseAirPollutionData(response);
                        addAirPollutionDataToMap(airPollutionDatas, mapboxMap);
                        Log.d(TAG, airPollutionDatas.get(0).getAqi().toString());

                    }
                });
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void addAirPollutionDataToMap(ArrayList<AirPollutionData> airPollutionDatas, MapboxMap mapboxMap) {
        for (int i = 0; i < airPollutionDatas.size(); i++) {
            AirPollutionData deviceData = airPollutionDatas.get(i);
            Icon icon = getIconForAirQualityIndex(deviceData.getAqi());
            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(deviceData.getLatitude(), deviceData.getLongitude()))
                    .title(deviceData.getLabel())
                    .snippet("AQI: " + deviceData.getAqi().toString()))
                    .setIcon(icon);
        }
    }

    private Icon getIconForAirQualityIndex(Integer aqi) {
        IconFactory iconFactory = IconFactory.getInstance(MainActivity.this);
        Drawable iconDrawable;
        if (0<=aqi.intValue() && aqi.intValue()<=50){
            iconDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.good_map_marker);
        }
        else if (50<aqi.intValue() && aqi.intValue()<=100){
            iconDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.satisfactory_map_marker);
        }
        else if (100<aqi.intValue() && aqi.intValue()<=200){
            iconDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.moderate_map_marker);
        }
        else if (200<aqi.intValue() && aqi.intValue()<=300){
            iconDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.poor_map_marker);
        }
        else if (300<aqi.intValue() && aqi.intValue()<=400){
            iconDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.very_poor_map_marker);
        }
        else if (400<aqi.intValue() && aqi.intValue()<=500){
            iconDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.severe_map_marker);
        }
        else {
            iconDrawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.moderate_map_marker);
        }
        Icon icon = iconFactory.fromDrawable(iconDrawable);
        return icon;
    }

    private ArrayList<AirPollutionData> parseAirPollutionData(JSONArray response) {
        JSONArray firstJsonArray = null;
        ArrayList<AirPollutionData> datas = new ArrayList<>();
        try {
            firstJsonArray = (JSONArray) response.get(0);
            for (int i = 0; i < firstJsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) firstJsonArray.get(i);
                Gson gson = new GsonBuilder().create();
                AirPollutionData deviceData = gson.fromJson(jsonObject.toString(), AirPollutionData.class);
                datas.add(deviceData);
            }


        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.om.demo.pollutionmap/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.om.demo.pollutionmap/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
