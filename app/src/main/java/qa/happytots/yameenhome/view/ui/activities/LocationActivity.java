package qa.happytots.yameenhome.view.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.os.ResultReceiver;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.BuildConfig;
import qa.happytots.yameenhome.DataBase.DatabaseHandler;
import qa.happytots.yameenhome.HomeActivity;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.Utility.UtilityClass;
import qa.happytots.yameenhome.components.YameenTextView;
import qa.happytots.yameenhome.datamodel.PreferenceController;
import qa.happytots.yameenhome.datamodel.network.NetworkManager;
import qa.happytots.yameenhome.datamodel.network.Response;
import qa.happytots.yameenhome.model.Constants;
import qa.happytots.yameenhome.model.location.LocationResponse;
import qa.happytots.yameenhome.model.location.Zone;
import qa.happytots.yameenhome.view.adapter.LocationAdapter;
import qa.happytots.yameenhome.view.base.BaseActivity;
import qa.happytots.yameenhome.view.services.LocationService;
import retrofit2.Call;

public class LocationActivity extends BaseActivity implements LocationAdapter.OnLocationInteractorListener {

    public static final String BUNDLE_IS_FROM_SPLASH = "is_from_splash";
    private static final String TAG = "LocationActivity";

    private List<Zone> mZones = new ArrayList<>();
    private LocationAdapter mAdapter;
    private YameenTextView tvCurrentLocation;

    private boolean isFromSplash;

    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Provides access to the Location Settings API.
     */
    private SettingsClient mSettingsClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private LocationSettingsRequest mLocationSettingsRequest;

    /**
     * Callback for Location events.
     */
    private LocationCallback mLocationCallback;

    /**
     * Represents a geographical location.
     */
    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        isFromSplash = getIntent().getBooleanExtra(BUNDLE_IS_FROM_SPLASH, false);
        Toolbar toolbar = findViewById(R.id.toolbar_location);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        tvCurrentLocation = findViewById(R.id.tv_current_location);
        RecyclerView rvLocations = findViewById(R.id.rv_locations);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvLocations.setLayoutManager(manager);

        String zoneId = PreferenceController.getStringPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOCATION_ID);
       // qa.happytots.yameenhome.components.Toast.makeToast("ZoneId="+zoneId);
        mAdapter = new LocationAdapter(zoneId, mZones, this);
        rvLocations.setAdapter(mAdapter);

        tvCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationUpdates();
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();

        getLocations();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                Intent intent = new Intent(LocationActivity.this, LocationService.class);
                intent.putExtra(Constants.LOCATION_DATA_EXTRA, mCurrentLocation);
                startService(intent);
                stopLocationUpdates();

            }
        };
    }


    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        qa.happytots.yameenhome.components.Toast.makeToast("Click on the Use current location for location");
                        break;
                    case Activity.RESULT_CANCELED:

                        break;
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(BuildConfig.APPLICATION_ID));
    }

    private void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        if (checkPermissions()) {
                            UtilityClass.showProgressDialog(LocationActivity.this);
                            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                    mLocationCallback, Looper.myLooper());
                        } else {
                            requestPermissions();
                        }
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
                                    rae.startResolutionForResult(LocationActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {

                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                qa.happytots.yameenhome.components.Toast.makeToast(errorMessage);
                        }


                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {

        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(LocationActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(LocationActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                qa.happytots.yameenhome.components.Toast.makeToast("You have canceled the permission");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

    private void getLocations() {
        UtilityClass.showProgressDialog(LocationActivity.this);
        DatabaseHandler handler = new DatabaseHandler(this);
        NetworkManager manager = NetworkManager.getInstance();
        manager.setNetworkListener(this);
        manager.callAPIForGetLocation(handler.getSessionValue());
    }

    @Override
    public void successResponse(Response response) {
        UtilityClass.dismissProgressDialog();
       // qa.happytots.yameenhome.components.Toast.makeToast("Location read success");
        if (response.body instanceof LocationResponse) {
            LocationResponse locationResponse = (LocationResponse) response.body;
            if (locationResponse.getData() != null) {
                List<Zone> zones = locationResponse.getData().getZone();
                if (zones != null && zones.size() > 0) {
                    mZones.addAll(zones);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }
    @Override
    public void failureResponse(Response response) {
       // DatabaseHandler handler = new DatabaseHandler(this);
     //   qa.happytots.yameenhome.components.Toast.makeToast("session:"+handler.getSessionValue()+" location api failed");
    }
    @Override
    public void onZoneClick(int position, Zone zone) {
        PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOCATION_ID, zone.getZoneId());
        goToNextScreen(zone);
        finish();
    }


    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            UtilityClass.dismissProgressDialog();
            int resultCode = intent.getIntExtra(Constants.RESULT_CODE, -1);
            if (resultCode == Constants.SUCCESS_RESULT) {
                String result = intent.getStringExtra(Constants.RESULT_DATA_KEY);
                if (result == null || result.length() == 0) {
                    qa.happytots.yameenhome.components.Toast.makeToast("Sorry current location not available, Please try again");
                    return;
                }
                compareWithList(result);
            }
        }
    };

    private void compareWithList(String district) {
        for (Zone zone : mZones) {
            if (zone.getName().equalsIgnoreCase(district)) {
                PreferenceController.setPreference(PreferenceController.PreferenceKeys.PREFERENCE_LOCATION_ID, zone.getZoneId());
                goToNextScreen(zone);
                break;
            }
        }
    }

    private void goToNextScreen(Zone zone) {
        if (isFromSplash) {
            Intent i = new Intent(LocationActivity.this, HomeActivity.class);
            startActivity(i);
        } else {
            Intent intent = new Intent();
            intent.putExtra(PreferenceController.PreferenceKeys.PREFERENCE_LOCATION_ID, zone.getZoneId());
            setResult(Activity.RESULT_OK, intent);
        }
        finish();
    }
}
