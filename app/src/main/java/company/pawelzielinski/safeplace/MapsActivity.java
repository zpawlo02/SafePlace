package company.pawelzielinski.safeplace;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import company.pawelzielinski.safeplace.Fragments.ADDPlace;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Circle circle;
    private CircleOptions circleOptions;

    private Button buttonIncreaseCircle, buttonDecreaseCircle, buttonSaveArea;

    private LatLng mCircleCenter = new LatLng(-34, 151);
    private int circleRadius;
    private Boolean isSafe;
    private String comment;
    private int traffic = 1, pickpockets = 1, kidnapping = 1, homeless = 1, publicTransport = 1,
            parties = 1, shops = 1, carthefts = 1, kids = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        Intent intent = getIntent();

        isSafe = intent.getBooleanExtra("isSafe", true);
        traffic =  intent.getIntExtra("traffic", 1);
        pickpockets =  intent.getIntExtra("pickpockets", 1);
        kidnapping =  intent.getIntExtra("kidnapping", 1);
        homeless =  intent.getIntExtra("homeless", 1);
        publicTransport =  intent.getIntExtra("publicTransport", 1);
        parties =  intent.getIntExtra("parties", 1);
        shops =  intent.getIntExtra("shops", 1);
        carthefts =  intent.getIntExtra("carthefts", 1);
        kids =  intent.getIntExtra("kids", 1);
        circleRadius = intent.getIntExtra("circleRadius",1);

        comment = intent.getStringExtra("comment");

        circleOptions = setOpions(isSafe);

        buttonIncreaseCircle = (Button) findViewById(R.id.buttonIncreaseCircle);
        buttonDecreaseCircle = (Button) findViewById(R.id.buttonDecreaseCircle);

        buttonIncreaseCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(circleRadius < 900){
                    circleRadius += 10;
                }

                circle.setRadius(circleRadius);
                circleOptions = setOpions(isSafe);

            }
        });

            buttonDecreaseCircle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(circleRadius > 20) {
                        circleRadius -= 10;
                    }
                    circle.setRadius(circleRadius);
                    circleOptions = setOpions(isSafe);

                }
            });

            buttonSaveArea = (Button) findViewById(R.id.buttonSaveArea);

            buttonSaveArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startAdding();
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
        /*mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });*/
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onBackPressed() {
        startAdding();
    }

    public CircleOptions setOpions(boolean isSafe){
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
        return circleOptions;
    }

    private void startAdding(){
        Bundle bundle = new Bundle();
        bundle.putString("comment", comment);
        bundle.putInt("traffic", traffic);
        bundle.putInt("pickpockets", pickpockets);
        bundle.putInt("kidnapping", kidnapping);
        bundle.putInt("homeless", homeless);
        bundle.putInt("publicTransport", publicTransport);
        bundle.putInt("parties", parties);
        bundle.putInt("shops", shops);
        bundle.putInt("carthefts", carthefts);
        bundle.putInt("kids", kids);
        bundle.putDouble("latitude", mCircleCenter.latitude);
        bundle.putDouble("longitude", mCircleCenter.longitude);
        bundle.putInt("circleRadius", circleRadius);
        bundle.putBoolean("isSafe", isSafe);
        FragmentManager fm = getSupportFragmentManager();
        Fragment add = new ADDPlace();
        ((ADDPlace) add).wasOpened = true;
        add.setArguments(bundle);
        fm.beginTransaction().add(R.id.fMaps, add).commit();
    }
}
