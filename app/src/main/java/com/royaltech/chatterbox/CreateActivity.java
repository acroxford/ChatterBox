package com.royaltech.chatterbox;

import android.app.Application;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class CreateActivity extends FragmentActivity implements /*OnMapReadyCallback OnMapClickListener */ LocationListener{
	
	private GoogleMap mMap;

    //Credit: https://www.parse.com/tutorials/anywall-android#5-user-location

    public static class ErrorDialogFragment extends DialogFragment {
        private Dialog mDialog;

        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }

    private boolean servicesConnected() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), Application.APPTAG);
            }
            return false;
        }
    }


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);

        GooglePlayServicesClient.ConnectionCallbacks,
                GooglePlayServicesClient.OnConnectionFailedListener {

            private LocationRequest locationRequest;
            private LocationClient locationClient;

            @Override
            protected void onCreate(Bundle savedInstanceState) {

                // Create a new global location parameters object
                locationRequest = LocationRequest.create();
                locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setFastestInterval(FAST_INTERVAL_CEILING_IN_MILLISECONDS);

                // Create a new location client, using the enclosing class to handle callbacks.
                locationClient = new LocationClient(this, this, this);

            }

        }

        private void startPeriodicUpdates() {
            locationClient.requestLocationUpdates(locationRequest, this);
        }

        private void stopPeriodicUpdates() {
            locationClient.removeLocationUpdates(this);
        }

        private Location getLocation() {
            if (servicesConnected()) {
                return locationClient.getLastLocation();
            } else {
                return null;
            }
        }

        public void onConnected(Bundle bundle) {
            currentLocation = getLocation();
            startPeriodicUpdates();
        }

        public void onConnectionFailed(ConnectionResult connectionResult) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                } catch (IntentSender.SendIntentException e) {
                }
            } else {
                showErrorDialog(connectionResult.getErrorCode());
            }
        }

        public void onLocationChanged(Location location) {
            currentLocation = location;
            if (lastLocation != null
                    && geoPointFromLocation(location)
                    .distanceInKilometersTo(geoPointFromLocation(lastLocation)) < 0.01) {
                return;
            }
            lastLocation = location;

            // Update the display
        }

        @Override
        public void onStop() {
            if (locationClient.isConnected()) {
                stopPeriodicUpdates();
            }
            locationClient.disconnect();

            super.onStop();
        }

        @Override
        public void onStart() {
            super.onStart();

            locationClient.connect();
        }


        //end


        //SupportMapFragment mapFragment =
             //   (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
       // mapFragment.getMapAsync(this);
		mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		
		mMap.setOnMapClickListener(this); 
	}
	

	//@Override
	public void onMapReady(GoogleMap arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//@Override
	public void onMapClick(LatLng position) {
		
		mMap.addMarker(new MarkerOptions().position(position).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
	}
}
