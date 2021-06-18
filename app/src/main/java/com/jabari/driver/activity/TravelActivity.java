package com.jabari.driver.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.jabari.driver.R;
import com.jabari.driver.activity.main.MainActivity;
import com.jabari.driver.controller.RequestController;
import com.jabari.driver.global.DigitConverter;
import com.jabari.driver.global.ExceptionHandler;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.History;

import org.json.JSONException;
import org.json.JSONObject;
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

public class TravelActivity extends AppCompatActivity {
    private TextView tv_code, tv_date, tv_cache, tv_stop, tv_return, tv_payment_side, tv_start_location, tv_end_location, tv_payment_right;
    private MapView map;
    VectorElementLayer markerLayer;
    final int BASE_MAP_INDEX = 0;
    final int POI_INDEX = 1;
    private ExceptionHandler handler;
    public History request;
    String id;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveldetail);
        handler = new ExceptionHandler(this);
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras() != null) {
                JSONObject object = null;
                try {
                    object = new JSONObject(intent.getStringExtra("me.cheshmak.data"));
                    id = object.getString("requestdId");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        initLayoutReferences();

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
        tv_payment_right = findViewById(R.id.tv_pay_right);
        ///////
        tv_code.setText(request.getId());
        tv_date.setText(DigitConverter.convert(request.getCreatedAt()));
        tv_cache.setText(DigitConverter.convert(String.valueOf(request.getCashPayment())));
        tv_start_location.setText(request.getLocationAddress());
        tv_end_location.setText(request.getDestinationAddress());
        if (request.isStop())
            tv_stop.setText("دارد");
        else tv_stop.setText("ندارد");
        if (request.isHaveReturn())
            tv_return.setText("دارد");
        else tv_return.setText("ندارد");
        if (request.isPayByRequest()) {
            tv_payment_side.setText("مبدا");
            tv_payment_right.setText("دارد");
        } else {
            tv_payment_side.setText("مقصد");
            tv_payment_right.setText("ندارد");
        }
    }

    private void initLayoutReferences() {
        map = findViewById(R.id.mapView);
        initMap();
        getRequest();

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

    private void getRequest() {
        if (id != null) {
            ApiInterface.RequestCallback requestCallback = new ApiInterface.RequestCallback() {
                @Override
                public void onResponse(History history) {
                    request = history;
                    setView();
                    addMarker(new LngLat(Double.parseDouble(history.getDestination().get(0)), Double.parseDouble(history.getDestination().get(1))));
                    addMarker(new LngLat(Double.parseDouble(history.getLocation().get(0)), Double.parseDouble(history.getLocation().get(1))));
                }

                @Override
                public void onFailure(String error) {

                }
            };
            RequestController requestController = new RequestController(requestCallback);
            requestController.getReadyRequests(id);
        } else
            handler.generateError("id");
    }

    public void onCancelClicked(View view) {
        startActivity(new Intent(TravelActivity.this, MainActivity.class));
    }

    private void addMarker(LngLat loc) {
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

    public void OnAcceptClick() {
        ApiInterface.AcceptedRequestCallback acceptedRequestCallback = new ApiInterface.AcceptedRequestCallback() {
            @Override
            public void onResponse() {
                handler.generateSuccess("accept");
            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }
        };
        RequestController requestController = new RequestController(acceptedRequestCallback);
        requestController.acceptRequest(request.getId());
    }
}
