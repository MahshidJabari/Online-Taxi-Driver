package com.jabari.driver.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.jabari.driver.R;
import com.jabari.driver.controller.LocationController;
import com.jabari.driver.network.config.ApiInterface;

import org.neshan.core.LngLat;
import org.neshan.core.Range;
import org.neshan.layers.Layer;
import org.neshan.layers.VectorElementLayer;
import org.neshan.services.NeshanMapStyle;
import org.neshan.services.NeshanServices;
import org.neshan.styles.AnimationStyle;
import org.neshan.styles.AnimationStyleBuilder;
import org.neshan.styles.AnimationType;
import org.neshan.styles.MarkerStyle;
import org.neshan.styles.MarkerStyleCreator;
import org.neshan.ui.MapView;
import org.neshan.utils.BitmapUtils;
import org.neshan.vectorelements.Marker;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TravelDetailsActivity extends AppCompatActivity {

    private MapView map;
    VectorElementLayer markerLayer;
    final int BASE_MAP_INDEX = 0;
    final int POI_INDEX = 1;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveldetail);


    }

    @Override
    protected void onStart() {
        super.onStart();
        initLayoutReferences();

    }

    private void initLayoutReferences() {
        map = findViewById(R.id.mapView);
        initMap();
        getLatLong();

    }

    private void initMap() {

        // add Standard_day map to layer BASE_MAP_INDEX
        map.getOptions().setZoomRange(new Range(4.5f, 18f));
        Layer baseMap = NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY, getCacheDir() + "/baseMap", 10);
        map.getLayers().insert(BASE_MAP_INDEX, baseMap);
        Layer poiLayer = NeshanServices.createPOILayer(false, getCacheDir() + "/poiLayer", 10);
        map.getLayers().insert(POI_INDEX, poiLayer);

        map.getLayers().insert(BASE_MAP_INDEX, NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY));

        // Setting map focal position to a fixed position and setting camera zoom
        map.setFocalPointPosition(new LngLat(51.330743, 35.767234), 0);
        map.setZoom(14, 0);
    }

    private void getLatLong() {

        ApiInterface.GetPointsCallback getPointsCallback = new ApiInterface.GetPointsCallback() {
            @Override
            public void onResponse(Location start, Location stop) {


                addMarker(new LngLat(start.getLongitude(), start.getLatitude()));
                addMarker(new LngLat(stop.getLongitude(), stop.getLatitude()));
            }

            @Override
            public void onFailure(String error) {

            }
        };
        LocationController locationController = new LocationController(getPointsCallback);
        locationController.Do();
    }

    private void addMarker(LngLat loc) {
        // First, we should clear every marker that is currently located on map
        markerLayer.clear();

        // Creating animation for marker. We should use an object of type AnimationStyleBuilder, set
        // all animation features on it and then call buildStyle() method that returns an object of type
        // AnimationStyle
        AnimationStyleBuilder animStBl = new AnimationStyleBuilder();
        animStBl.setFadeAnimationType(AnimationType.ANIMATION_TYPE_SMOOTHSTEP);
        animStBl.setSizeAnimationType(AnimationType.ANIMATION_TYPE_SPRING);
        animStBl.setPhaseInDuration(0.5f);
        animStBl.setPhaseOutDuration(0.5f);
        AnimationStyle animSt = animStBl.buildStyle();

        // Creating marker style. We should use an object of type MarkerStyleCreator, set all features on it
        // and then call buildStyle method on it. This method returns an object of type MarkerStyle
        MarkerStyleCreator markStCr = new MarkerStyleCreator();
        markStCr.setSize(20f);
        markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location)));
        // AnimationStyle object - that was created before - is used here
        markStCr.setAnimationStyle(animSt);
        MarkerStyle markSt = markStCr.buildStyle();

        // Creating marker
        Marker marker = new Marker(loc, markSt);

        // Adding marker to markerLayer, or showing marker on map!
        markerLayer.add(marker);
    }
}
