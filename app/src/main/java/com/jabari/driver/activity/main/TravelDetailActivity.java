package com.jabari.driver.activity.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jabari.driver.R;
import com.jabari.driver.activity.HistoryActivity;
import com.jabari.driver.global.DigitConverter;
import com.jabari.driver.network.model.History;

import org.neshan.core.LngLat;
import org.neshan.core.Range;
import org.neshan.layers.Layer;
import org.neshan.layers.VectorElementLayer;
import org.neshan.services.NeshanMapStyle;
import org.neshan.services.NeshanServices;
import org.neshan.styles.MarkerStyle;
import org.neshan.styles.MarkerStyleCreator;
import org.neshan.ui.MapView;
import org.neshan.utils.BitmapUtils;
import org.neshan.vectorelements.Marker;


import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TravelDetailActivity extends AppCompatActivity {

    private TextView tv_code, tv_date, tv_cache, tv_stop, tv_return, tv_payment_side, tv_start_location, tv_end_location, tv_driver, tv_payment_right;
    private History history;
    private MapView map;
    private boolean startPosSelected = false;
    VectorElementLayer startMarker, endMarker;
    final int POI_INDEX = 1;
    final int BASE_MAP_INDEX = 0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        history = (History) getIntent().getSerializableExtra("history");
        setView();
        initLayoutReferences();
    }

    public void onBackClick(View view) {
        startActivity(new Intent(TravelDetailActivity.this, HistoryActivity.class));
    }

    private void setView() {

        tv_code = findViewById(R.id.tv_code);
        tv_date = findViewById(R.id.tv_date);
        tv_cache = findViewById(R.id.tv_cache);
        tv_stop = findViewById(R.id.tv_stop);
        tv_return = findViewById(R.id.tv_money_back);
        tv_payment_side = findViewById(R.id.tv_payment_side);
        tv_start_location = findViewById(R.id.tv_start_address);
        tv_end_location = findViewById(R.id.tv_destination_address);
        tv_driver = findViewById(R.id.tv_driver);
        tv_payment_right = findViewById(R.id.tv_pay_right);
        ///////
        tv_code.setText(history.getId());
        tv_date.setText(DigitConverter.convert(history.getCreatedAt()));
        tv_cache.setText(DigitConverter.convert(String.valueOf(history.getCashPayment())));
        tv_driver.setText(history.getDriver());
        tv_start_location.setText(history.getLocationAddress());
        tv_end_location.setText(history.getDestinationAddress());
        if (history.isStop())
            tv_stop.setText("دارد");
        else tv_stop.setText("ندارد");
        if (history.isHaveReturn())
            tv_return.setText("دارد");
        else tv_return.setText("ندارد");
        if (history.isPayByRequest()) {
            tv_payment_side.setText("مبدا");
            tv_payment_right.setText("دارد");
        } else {
            tv_payment_side.setText("مقصد");
            tv_payment_right.setText("ندارد");
        }
    }

    private void initLayoutReferences() {
        map = findViewById(R.id.mapView);
        ;
        initMap();
    }

    private void initMap() {
        // Creating a VectorElementLayer(called userMarkerLayer) to add user marker to it and adding it to map's layers
        startMarker = NeshanServices.createVectorElementLayer();
        endMarker = NeshanServices.createVectorElementLayer();
        map.getLayers().add(startMarker);
        map.getLayers().add(endMarker);

        // add Standard_day map to layer BASE_MAP_INDEX
        map.getOptions().setZoomRange(new Range(4.5f, 18f));
        Layer baseMap = NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY, getCacheDir() + "/baseMap", 10);
        map.getLayers().insert(BASE_MAP_INDEX, baseMap);
        Layer poiLayer = NeshanServices.createPOILayer(false, getCacheDir() + "/poiLayer", 10);
        map.getLayers().insert(POI_INDEX, poiLayer);

        map.getLayers().insert(BASE_MAP_INDEX, NeshanServices.createBaseMap(NeshanMapStyle.STANDARD_DAY));

        // Setting map focal position to a fixed position and setting camera zoom
        map.setFocalPointPosition(new LngLat(51.330743, 35.767234), 0);
        if (history.getLocation().get(0) != null & history.getLocation().get(1) != null) {
            addMarker(new LngLat(Double.parseDouble(history.getLocation().get(0)), Double.parseDouble(history.getLocation().get(1))));
            startPosSelected = true;

        }
        /*if (travel.getDestination().get(0) != null & travel.getDestination().get(1) != null) {
            addMarker(new LngLat(Double.parseDouble(travel.getDestination().get(0)), Double.parseDouble(travel.getDestination().get(1))));
        }*/

        map.setZoom(15, 0);
    }

    private void addMarker(LngLat loc) {

        if (!startPosSelected) {
            MarkerStyleCreator markStCr = new MarkerStyleCreator();
            markStCr.setSize(40f);
            markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location)));
            MarkerStyle markSt = markStCr.buildStyle();
            // Creating marker
            Marker marker = new Marker(loc, markSt);
            // Adding marker to markerLayer, or showing marker on map!
            startMarker.add(marker);
            focusOnLocation(loc);

        }
      /*  else {  MarkerStyleCreator markStCr = new MarkerStyleCreator();
            markStCr.setSize(40f);
            markStCr.setBitmap(BitmapUtils.createBitmapFromAndroidBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location)));
            MarkerStyle markSt = markStCr.buildStyle();
            // Creating marker
            Marker marker = new Marker(loc, markSt);
            // Adding marker to markerLayer, or showing marker on map!
            endMarker.add(marker);}*/

    }

    public void focusOnLocation(LngLat location) {
        if (startMarker != null) {
            map.setFocalPointPosition(location, 0.25f);
            map.setZoom(15, 0.25f);
        }
    }
}
