package com.jabari.driver.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jabari.driver.R;
import com.jabari.driver.activity.main.MainActivity;

import org.neshan.core.LngLat;
import org.neshan.core.Range;
import org.neshan.layers.Layer;
import org.neshan.layers.VectorElementLayer;
import org.neshan.services.NeshanMapStyle;
import org.neshan.services.NeshanServices;
import org.neshan.ui.MapView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AreaActivity extends AppCompatActivity {

    private LinearLayout lin;
    private MapView map;
    final int BASE_MAP_INDEX = 0;
    private CheckBox cb_high_req,cb_few_req,cb_without;
    private VectorElementLayer userMarkerLayer;
    final int POI_INDEX = 1;
    private TextView tv_return;
    private ImageView img_return;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        setView();
        backOnclick();
    }

    private void setView(){
        lin = findViewById(R.id.lin5);
        lin.bringToFront();
        cb_high_req = findViewById(R.id.cb_high_req);
        cb_few_req = findViewById(R.id.cb_few_req);
        cb_without = findViewById(R.id.cb_without_req);
        tv_return = findViewById(R.id.tv_return);
        img_return = findViewById(R.id.img_return);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initLayoutReferences();

     /*   initLocation();
        startReceivingLocationUpdates();
    */}

    private void initLayoutReferences() {
        map = findViewById(R.id.mapView);
        initMap();
    }

    private void initMap() {
        // Creating a VectorElementLayer(called userMarkerLayer) to add user marker to it and adding it to map's layers
        userMarkerLayer = NeshanServices.createVectorElementLayer();
        map.getLayers().add(userMarkerLayer);

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

    private void backOnclick(){
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AreaActivity.this, MainActivity.class));
            }
        });
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AreaActivity.this,MainActivity.class));
            }
        });

    }


}
