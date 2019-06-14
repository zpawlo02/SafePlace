package company.pawelzielinski.safeplace.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.MapsActivity;
import company.pawelzielinski.safeplace.R;

public class PlacesListAdapter extends ArrayAdapter<Place> {

    private static final String TAG = "PlacesListAdapter";

    private Context context;
    int mResource;

    public PlacesListAdapter(Context context, int resource, ArrayList<Place> objects) {
        super(context, resource, objects);
        this.context = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        boolean isSafe = getItem(position).getisSafe();

        int carthefts = getItem(position).getCarthefts();
        int homeless = getItem(position).getHomeless();
        int kidnapping = getItem(position).getKidnapping();
        int kids = getItem(position).getKids();
        int parties = getItem(position).getParties();
        int pickpockets = getItem(position).getPickpockets();
        int publicTransport = getItem(position).getPublicTransport();
        int shops = getItem(position).getShops();
        int traffic = getItem(position).getTraffic();
        int circleRadius = getItem(position).getCircleRadius();

        final double lat = getItem(position).getLat();
        double longT = getItem(position).getLongT();
        double rating = getItem(position).getRating();

        final LatLng latLng = getItem(position).getLatLng();
        String comment = getItem(position).getComment();

        String placeId = getItem(position).getPlaceId();
        String adress = getItem(position).getAdress();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);
        View v = inflater.inflate(R.layout.adapter_view_layout,null,true);

        TextView textViewSafeNotSafeItem = (TextView) v.findViewById(R.id.textViewSafeNotSafeItem);
        TextView textViewCountryCity = (TextView) v.findViewById(R.id.textViewCountryCity);
        TextView textViewRatingNumber = (TextView) v.findViewById(R.id.textViewRatingNumber);
         final MapView mapView = (MapView) v.findViewById(R.id.imageViewMapShow);
        if(isSafe == true){
            textViewSafeNotSafeItem.setText("Safe");
        }else {
            textViewSafeNotSafeItem.setText("Not safe");
        }

        textViewCountryCity.setText(adress);
        textViewRatingNumber.setText(String.valueOf(rating));

        CircleOptions circleOptions;
        if(!isSafe){
            circleOptions = new CircleOptions()
                    .strokeWidth(4)
                    .radius(circleRadius)
                    .center(latLng)
                    .strokeColor(Color.parseColor("#490033"))
                    .fillColor(Color.argb(50,230, 0, 0));
        }
        else{
            circleOptions = new CircleOptions()
                    .strokeWidth(4)
                    .radius(circleRadius)
                    .center(latLng)
                    .strokeColor(Color.parseColor("#490033"))
                    .fillColor(Color.argb(50,0, 230, 0));
        }

        final CircleOptions circ = circleOptions;

        MapsInitializer.initialize(getContext());

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
             //   mapView = googleMap;
                GoogleMap map = googleMap;
                map.addCircle(circ);
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });

        //mapView.
//        mapView.onResume();

        return v;
    }


}


