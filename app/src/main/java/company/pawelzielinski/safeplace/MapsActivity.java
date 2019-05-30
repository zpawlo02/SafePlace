package company.pawelzielinski.safeplace;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Circle circle;
    private CircleOptions circleOptions;
    private LatLng mCircleCenter = new LatLng(-34, 151);
    private Button buttonIncreaseCircle, buttonDecreaseCircle;

    private Double circleRadius = 250.0;
    private Boolean isSafe;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Intent intent = getIntent();

        isSafe = intent.getBooleanExtra("isSafe", true);
        if(!isSafe){
            circleOptions = new CircleOptions()
                    .strokeWidth(4)
                    .radius(circleRadius)
                    .center(mCircleCenter)
                    .strokeColor(Color.parseColor("#490033"))
                    .fillColor(Color.argb(50,230, 0, 0));
        }
        else{
            circleOptions = new CircleOptions()
                    .strokeWidth(4)
                    .radius(circleRadius)
                    .center(mCircleCenter)
                    .strokeColor(Color.parseColor("#490033"))
                    .fillColor(Color.argb(50,0, 230, 0));
        }


        buttonIncreaseCircle = (Button) findViewById(R.id.buttonIncreaseCircle);
        buttonDecreaseCircle = (Button) findViewById(R.id.buttonDecreaseCircle);

        buttonIncreaseCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), circleRadius.toString(), Toast.LENGTH_SHORT);
                circleRadius += 10;
                circle.setRadius(circleRadius);

            }
        });



            buttonDecreaseCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(circleRadius > 20) {
                        circleRadius -= 10;
                    }
                    Toast.makeText(getApplicationContext(), circleRadius.toString(), Toast.LENGTH_SHORT);
                    circle.setRadius(circleRadius);

                }
            });



        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mCircleCenter = latLng;
                MarkerOptions markerOptions = new MarkerOptions();
                /*if(!isSafe){
                    circle = mMap.addCircle(new CircleOptions()
                            .strokeWidth(4)
                            .radius(circleRadius)
                            .center(mCircleCenter)
                            .strokeColor(Color.parseColor("#490033"))
                            .fillColor(Color.argb(50,230, 0, 0)));
                }
                else{
                    circle = mMap.addCircle(new CircleOptions()
                            .strokeWidth(4)
                            .radius(circleRadius)
                            .center(mCircleCenter)
                            .strokeColor(Color.parseColor("#490033"))
                            .fillColor(Color.argb(50,0, 230, 0)));
                }*/

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
                circleOptions.center(latLng);
                circle = mMap.addCircle(circleOptions);
            }

        });
        Log.i("sssse",Double.toString( mCircleCenter.latitude));


        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
      /*  mMap.addCircle(new CircleOptions()
                .strokeWidth(4)
                .radius(250)
                .center(mCircleCenter)
                .strokeColor(Color.parseColor("#D1C4E9"))
                .fillColor(Color.parseColor("#657C4DFF")));*/
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



}
