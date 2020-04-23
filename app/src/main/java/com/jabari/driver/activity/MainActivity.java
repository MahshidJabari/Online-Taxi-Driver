package com.jabari.driver.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.jabari.driver.R;
import com.jabari.driver.activity.account.ProfileActivity;
import com.jabari.driver.controller.LoginController;
import com.jabari.driver.controller.UserController;
import com.jabari.driver.global.ExceptionHandler;
import com.jabari.driver.network.config.ApiInterface;
import com.jabari.driver.network.model.Coordinate;
import com.jabari.driver.network.model.User;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab_pro, fab_reward, fab_salary, fab_home;
    private LinearLayout ln_finance, ln_score, ln_travel_list, ln_area, ln_salary;
    private CheckBox check_net, check_GPS, cb_ready;
    private User currentUser;
    private ExceptionHandler handler;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 300000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 300000;
    final int REQUEST_CODE = 123;
    int PERMISSION_ID = 44;
    private Location userLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;
    private LocationCallback locationCallback;
    private Boolean mRequestingLocationUpdates;
    private String lastUpdateTime;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new ExceptionHandler(this);

        setView();
        setLinOnclickListener();
        setFabOnClickListener();
        setCheckBox();
        getCurrentUser();
        onReady();


    }

    public void onReady() {
        cb_ready.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb_ready.isChecked()) {
                    initLocation();
                    startReceivingLocationUpdates();
                } else {
                    Coordinate coordinate = new Coordinate();
                    coordinate.setLongitude(String.valueOf(userLocation.getLongitude()));
                    coordinate.setLatitude(String.valueOf(userLocation.getLatitude()));
                    setDriverVisible(coordinate, false);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    public void setView() {

        //set check box
        check_net = findViewById(R.id.check_net);
        check_GPS = findViewById(R.id.check_GPS);
        cb_ready = findViewById(R.id.cb_ready);


        //set FloatingActionButton view
        fab_pro = findViewById(R.id.fab_pro);
        fab_reward = findViewById(R.id.fab_reward);
        fab_salary = findViewById(R.id.fab_salary);
        fab_home = findViewById(R.id.fab_home);
        fab_pro.bringToFront();
        fab_reward.bringToFront();
        fab_salary.bringToFront();
        fab_home.bringToFront();


        //set linear layout view
        ln_finance = findViewById(R.id.ln_finance);
        ln_score = findViewById(R.id.ln_score);
        ln_travel_list = findViewById(R.id.ln_travel_list);
        ln_area = findViewById(R.id.ln_area);
        ln_salary = findViewById(R.id.ln_salary);


    }

    private void setFabOnClickListener() {
        //set on click listener to fab
        fab_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("User", (Parcelable) currentUser);
                startActivity(intent);

            }
        });

        fab_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SalaryActivity.class);
                startActivity(intent);

            }
        });

        fab_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RewardActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setLinOnclickListener() {
        //set on click listener to linear layouts
        ln_finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SalaryDetailActivity.class);
                startActivity(intent);
            }
        });

        ln_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(intent);

            }
        });
        ln_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AreaActivity.class);
                startActivity(intent);

            }
        });
        ln_travel_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);

            }
        });
        ln_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SalaryActivity.class);
                startActivity(intent);

            }
        });

    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean isGPSConnected() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        }
        return true;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void setCheckBox() {

        if (isNetworkConnected()) {
            check_net.setChecked(true);
        } else
            check_net.setChecked(false);
        if (isGPSConnected())
            check_GPS.setChecked(true);
        else
            check_GPS.setChecked(false);

        check_GPS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (check_GPS.isChecked()) {
                    openSettings();
                }

            }
        });
    }

    private void getCurrentUser() {

        ApiInterface.CurrentUserCallback currentUserCallback = new ApiInterface.CurrentUserCallback() {
            @Override
            public void onResponse(User user) {
                currentUser = new User();
                currentUser = user;
            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }
        };
        LoginController loginController = new LoginController(currentUserCallback);
        loginController.getCurrentUser();
    }

    private void updateUserLocation(Coordinate coordinate) {

        ApiInterface.UpdateLocationCallback updateLocationCallback = new ApiInterface.UpdateLocationCallback() {
            @Override
            public void onResponse() {

            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }
        };
        UserController userController = new UserController(updateLocationCallback);
        userController.updateLocation(coordinate);
    }

    private void initLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        settingsClient = LocationServices.getSettingsClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                userLocation = locationResult.getLastLocation();
                Log.d("location", userLocation.toString());
                lastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                Coordinate coordinate = new Coordinate();
                coordinate.setLatitude(String.valueOf(userLocation.getLatitude()));
                coordinate.setLongitude(String.valueOf(userLocation.getLongitude()));
                setDriverVisible(coordinate, true);
            }
        };

        mRequestingLocationUpdates = false;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();

    }

    private void startLocationUpdates() {
        settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        //noinspection MissingPermission
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        Coordinate coordinate = new Coordinate();
                        coordinate.setLatitude(String.valueOf(userLocation.getLatitude()));
                        coordinate.setLongitude(String.valueOf(userLocation.getLongitude()));
                        updateUserLocation(coordinate);
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MainActivity.this, REQUEST_CODE);
                                } catch (IntentSender.SendIntentException sie) {

                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                handler.generateError("location setting");
                        }
                        Coordinate coordinate = new Coordinate();
                        coordinate.setLatitude(String.valueOf(userLocation.getLatitude()));
                        coordinate.setLongitude(String.valueOf(userLocation.getLongitude()));
                        updateUserLocation(coordinate);
                    }
                });

    }

    public void stopLocationUpdates() {
        // Removing location updates
        fusedLocationClient
                .removeLocationUpdates(locationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        handler.generateError("update location stopped");
                    }
                });
    }

    public void startReceivingLocationUpdates() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        break;
                    case Activity.RESULT_CANCELED:
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }


    private void setDriverVisible(Coordinate coordinate, Boolean visibility) {

        ApiInterface.VisibilityCallback visibilityCallback = new ApiInterface.VisibilityCallback() {
            @Override
            public void onResponse(String success) {
                handler.generateSuccess(success);
            }

            @Override
            public void onFailure(String error) {
                handler.generateError(error);
            }
        };
        UserController userController = new UserController(visibilityCallback);
        userController.setVisibility(coordinate, visibility);
    }

}
