package usama.utech.firebasepractice.AllPostsWork;

import android.os.Bundle;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;
import usama.utech.firebasepractice.R;

public class ShowRouteOnMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String startL, startLN, endL, endLN;
    LatLng start, waypoint, end;
    private List<Polyline> polylines;

    private static final int[] COLORS = new int[]{R.color.primary_dark1,R.color.primary1,R.color.primary_light1,R.color.accent1,R.color.primary_dark};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route_on_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    void getIntentdata() {


        startL = getIntent().getStringExtra("latstart");
        startLN = getIntent().getStringExtra("latend");
        endL = getIntent().getStringExtra("lngstart");
        endLN = getIntent().getStringExtra("lngend");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        getIntentdata();

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);



        start = new LatLng(Double.parseDouble(startL), Double.parseDouble(endL));

        end = new LatLng(Double.parseDouble(startLN), Double.parseDouble(endLN));



//        start = new LatLng(33.997797, 71.490276);
//        waypoint= new LatLng(18.01455, -77.499333);
//        end = new LatLng(34.014011, 71.544101);

//
//        mMap.addMarker(new MarkerOptions().position(start).title("Start Point"));
//        mMap.addMarker(new MarkerOptions().position(end).title("End Point"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        Routing routing = new Routing.Builder()
                .travelMode(Routing.TravelMode.DRIVING)
                .withListener(new RoutingListener() {
                    @Override
                    public void onRoutingFailure(RouteException e) {
                        Toast.makeText(ShowRouteOnMap.this, "failer", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onRoutingStart() {
                        Toast.makeText(ShowRouteOnMap.this, "start", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onRoutingSuccess(ArrayList<Route> route, int position)
                    {
                        Toast.makeText(ShowRouteOnMap.this, "Success", Toast.LENGTH_SHORT).show();





//                        if(polylines.size()>0) {
//                            for (Polyline poly : polylines) {
//                                poly.remove();
//                            }
//                        }

                        polylines = new ArrayList<>();
                        //add route(s) to the map.
                        for (int i = 0; i <route.size(); i++) {

                            //In case of more than 5 alternative routes
                            int colorIndex = i % COLORS.length;

                            PolylineOptions polyOptions = new PolylineOptions();
                            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
                            polyOptions.width(10 + i * 3);
                            polyOptions.addAll(route.get(i).getPoints());
                            Polyline polyline = mMap.addPolyline(polyOptions);
                            polylines.add(polyline);

                            Toast.makeText(getApplicationContext(),"Route "+ (i+1) +": distance - "+ route.get(i).getDistanceValue()+": duration - "+ route.get(i).getDurationValue(),Toast.LENGTH_SHORT).show();




                        }

                        // Start marker
                   mMap.addMarker(new MarkerOptions().position(start).title("Start Point"));

                        // End marker

                     mMap.addMarker(new MarkerOptions().position(end).title("End Point"));


                        CameraPosition cameraPosition = new CameraPosition.Builder().target(start).zoom(13.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        mMap.animateCamera(cameraUpdate);

                    }

                    @Override
                    public void onRoutingCancelled() {
                        Toast.makeText(ShowRouteOnMap.this, "cancelled", Toast.LENGTH_SHORT).show();

                    }
                })
                .waypoints(start,end)
                .key(getResources().getString(R.string.google_maps_key))
//                .alternativeRoutes(true)
                .build();
        routing.execute();
    }
}
