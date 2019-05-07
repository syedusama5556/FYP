package usama.utech.firebasepractice;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import usama.utech.firebasepractice.AllPostsWork.ListAllPosts;
import usama.utech.firebasepractice.AllPostsWork.PostYourTravel;
import usama.utech.firebasepractice.ModelClasses.User;

public class HomePageMap extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    private EditText name;
    private EditText age;
    private Button addData;
    private Button getData;
    private RecyclerView showDataTxt;
    ArrayList<User> data = new ArrayList<>();

    BottomAppBar bottomapp;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;

    private GoogleMap mMap;

    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 20000; /* 20 sec */

    private LocationManager locationManager;
    private LatLng latLng;
    private boolean isPermission;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_map);
        bottomapp = findViewById(R.id.bottomAppBar);

        setSupportActionBar(bottomapp);


        // Write a message to the database
        database = FirebaseDatabase.getInstance();

        if (requestSinglePermission()) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            //it was pre written
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            checkLocation(); //check whether location service is enable or not in your  phone
        }



    }



        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setTrafficEnabled(false);
            mMap.setIndoorEnabled(false);
            mMap.setBuildingsEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setTiltGesturesEnabled(true);
         //   mMap.getUiSettings().setZoomControlsEnabled(true);

            if (latLng != null) {
                mMap.clear();
              //  mMap.addMarker(new MarkerOptions().position(latLng).title("Your Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.navigation)));
               // CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15.0f).build();
                //CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
              //  mMap.animateCamera(cameraUpdate);
                mMap.setMyLocationEnabled(true);

            }


            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng2) {

                    mMap.clear();
                    mMap.addMarker(new MarkerOptions()
                          .position(latLng2)
                          .title("Your Selected Location"));


                    System.err.println("your address is "+getCompleteAddressString(latLng2.latitude,latLng2.longitude));

                }
            });




        }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

        @Override
        public void onConnected(@Nullable Bundle bundle) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            startLocationUpdates();

            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLocation == null) {
                startLocationUpdates();
            }
            if (mLocation != null) {

                // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
                //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
            } else {
                Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onConnectionSuspended(int i) {
            Log.i(TAG, "Connection Suspended");
            mGoogleApiClient.connect();
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Toast.makeText(this, "Connection failed. Error: " + connectionResult.getErrorCode(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLocationChanged(Location location) {
            String msg = "Updated Location: " +
                    Double.toString(location.getLatitude()) + "," +
                    Double.toString(location.getLongitude());
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            // You can now create a LatLng Object for use with maps
            latLng = new LatLng(location.getLatitude(), location.getLongitude());

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            //it was pre written
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            SharedPreferences prefs = getSharedPreferences("saveddata", MODE_PRIVATE);
            String currentlogitude = prefs.getString("currentlogitude", "");
            String currentlatitude = prefs.getString("currentlatitude", "");
            String uid = prefs.getString("uid", "");

            myRef = database.getReference("Drivers").child(uid);

            HashMap<String, String> updateLocationMap = new HashMap<>();
            updateLocationMap.put("currentlogitude", String.valueOf(location.getLongitude()));
            updateLocationMap.put("currentlatitude", String.valueOf(location.getLatitude()));

            myRef.updateChildren((HashMap) updateLocationMap);



        }



        protected void startLocationUpdates() {
            // Create the location request
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(UPDATE_INTERVAL)
                    .setFastestInterval(FASTEST_INTERVAL);
            // Request location updates
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
            Log.d("reque", "--->>>>");
        }

        @Override
        protected void onStart() {
            super.onStart();
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            }
        }

        @Override
        protected void onStop() {
            super.onStop();
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }

        private boolean checkLocation() {
            if (!isLocationEnabled())
                showAlert();
            return isLocationEnabled();
        }

        private void showAlert() {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Enable Location")
                    .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                            "use this app")
                    .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        }
                    });
            dialog.show();
        }

        private boolean isLocationEnabled() {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

        private boolean requestSinglePermission() {

            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            //Single Permission is granted
                            Toast.makeText(getApplicationContext(), "Single permission is granted!", Toast.LENGTH_SHORT).show();
                            isPermission = true;



                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            // check for permanent denial of permission
                            if (response.isPermanentlyDenied()) {
                                isPermission = false;



                            }
                            final AlertDialog.Builder dialog = new AlertDialog.Builder(HomePageMap.this);
                            dialog.setTitle("Location Permission")
                                    .setMessage("You have to give location permission!!, the app will close now")

                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                            finishAffinity();
                                        }
                                    });
                            dialog.setCancelable(false);
                            dialog.show();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();

            return isPermission;

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottomappbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.navigation_explore){


            startActivity(new Intent(getApplicationContext(), PostYourTravel.class));
        }

        if (item.getItemId() == R.id.navigation_profile){


            startActivity(new Intent(getApplicationContext(), ListAllPosts.class));
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}