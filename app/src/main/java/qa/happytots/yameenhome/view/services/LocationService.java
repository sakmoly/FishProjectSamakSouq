package qa.happytots.yameenhome.view.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import qa.happytots.yameenhome.BuildConfig;
import qa.happytots.yameenhome.R;
import qa.happytots.yameenhome.model.Constants;

public class LocationService extends IntentService {

    private static final String TAG = "LocationService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    public LocationService() {
        super("LocationService");
    }


    @Override
    protected void onHandleIntent(@NonNull Intent intent) {
        String errorMessage = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        Location location = intent.getParcelableExtra(
                Constants.LOCATION_DATA_EXTRA);

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just a single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available);
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = getString(R.string.invalid_lat_long_used);
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " +
                    location.getLongitude(), illegalArgumentException);
        }

        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
        } else {
            Address address = addresses.get(0);
            Log.i(TAG, getString(R.string.address_found));
            deliverResultToReceiver(Constants.SUCCESS_RESULT, address.getSubAdminArea());
        }
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Intent intent = new Intent(BuildConfig.APPLICATION_ID);
        intent.putExtra(Constants.RESULT_CODE, resultCode);
        intent.putExtra(Constants.RESULT_DATA_KEY, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
