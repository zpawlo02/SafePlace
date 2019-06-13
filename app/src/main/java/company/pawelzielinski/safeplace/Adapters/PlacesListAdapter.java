package company.pawelzielinski.safeplace.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        double lat = getItem(position).getLat();
        double longT = getItem(position).getLongT();
        double rating = getItem(position).getRating();

        LatLng latLng = getItem(position).getLatLng();
        String comment = getItem(position).getComment();

        String placeId = getItem(position).getPlaceId();
        String adress = getItem(position).getAdress();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(mResource,parent,false);

        TextView textViewSafeNotSafeItem = (TextView) convertView.findViewById(R.id.textViewSafeNotSafeItem);
        TextView textViewCountryCity = (TextView) convertView.findViewById(R.id.textViewCountryCity);
        TextView textViewRatingNumber = (TextView) convertView.findViewById(R.id.textViewRatingNumber);
        ImageView mapShow = (ImageView) convertView.findViewById(R.id.imageViewMapShow);
        String url;
        if(isSafe == true){
            url = "http://maps.google.com/maps/api/staticmap?center=" + lat + ","+ longT + "&zoom=15&size=200x200&markers=color:green%7C" + lat + ","+ longT +"&key=AIzaSyAyoJY1iGPD-CobXByRfBKbGiWl4H29fSc";
        }else {
            url = "http://maps.google.com/maps/api/staticmap?center=" + lat + ","+ longT + "&zoom=15&size=200x200&markers=color:red%7C" + lat + ","+ longT +"&key=AIzaSyAyoJY1iGPD-CobXByRfBKbGiWl4H29fSc";

        }

        Picasso.get().load(url).into(mapShow);

        return convertView;
    }

}


