package company.pawelzielinski.safeplace.Classes;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Place {

    public boolean isSafe;

    public int carthefts;
    public int homeless;
    public int kidnapping;
    public int kids;
    public int parties;
    public int pickpockets;
    public int publicTransport;
    public int shops;
    public int traffic;
    public int circleRadius;

    public double lat;
    public double longT;
    public double rating;

    public LatLng latLng;
    public String comment;

    public String country, city;
    public String adress;


   /* public Place(Context context, boolean isSafe, int carthefts, int homeless, int kidnapping,
                 int kids, int parties, int pickpockets,
                 int publicTransport, int shops, int traffic, int circleRadius,
                 double lat, double longT, double rating, LatLng latLng,
                 String comment ) {

        Geocoder geocoder;
        List<Address> adresses = null;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            adresses = geocoder.getFromLocation(lat,longT,1);
        }
        catch (IOException e){
            Log.i("ADRESSERROR", e.toString());
        }

        this.isSafe = isSafe;
        this.carthefts = carthefts;
        this.homeless = homeless;
        this.kidnapping = kidnapping;
        this.kids = kids;
        this.parties = parties;
        this.pickpockets = pickpockets;
        this.publicTransport = publicTransport;
        this.shops = shops;
        this.traffic = traffic;
        this.circleRadius = circleRadius;
        this.lat = lat;
        this.longT = longT;
        this.rating = rating;
        this.comment = comment;
       // this.placeId = placeId;

        this.adress = adresses.get(0).getAddressLine(0);
    }*/

    public Place(Context context, boolean isSafe, int carthefts, int homeless, int kidnapping, int kids, int parties, int pickpockets, int publicTransport, int shops, int traffic, int circleRadius, double lat, double longt, double rating,  String comment) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> adresses = null;


        try {
            adresses = geocoder.getFromLocation(lat,longt,1);
            Log.i("ADRESSERROR", String.valueOf(adresses.get(0)));
        }
        catch (IOException e){
            Log.i("ADRESSERROR", e.toString());
        }

        this.isSafe = isSafe;
        this.carthefts = carthefts;
        this.homeless = homeless;
        this.kidnapping = kidnapping;
        this.kids = kids;
        this.parties = parties;
        this.pickpockets = pickpockets;
        this.publicTransport = publicTransport;
        this.shops = shops;
        this.traffic = traffic;
        this.circleRadius = circleRadius;
        this.lat = lat;
        this.longT = longt;
        this.rating = rating;
        this.country = adresses.get(0).getCountryName();
        this.city = adresses.get(0).getLocality();
        this.comment = comment;

        this.adress = adresses.get(0).getAddressLine(0);
    }

    public Place(){

    }

    public boolean getisSafe() {
        return isSafe;
    }

    public int getCarthefts() {
        return carthefts;
    }

    public int getHomeless() {
        return homeless;
    }

    public int getKidnapping() {
        return kidnapping;
    }

    public int getKids() {
        return kids;
    }

    public int getParties() {
        return parties;
    }

    public int getPickpockets() {
        return pickpockets;
    }

    public int getPublicTransport() {
        return publicTransport;
    }

    public int getShops() {
        return shops;
    }

    public int getTraffic() {
        return traffic;
    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public double getLat() {
        return lat;
    }

    public double getLongT() {
        return longT;
    }

    public double getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getAdress() {
        return adress;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }

    public void setCarthefts(int carthefts) {
        this.carthefts = carthefts;
    }

    public void setHomeless(int homeless) {
        this.homeless = homeless;
    }

    public void setKidnapping(int kidnapping) {
        this.kidnapping = kidnapping;
    }

    public void setKids(int kids) {
        this.kids = kids;
    }

    public void setParties(int parties) {
        this.parties = parties;
    }

    public void setPickpockets(int pickpockets) {
        this.pickpockets = pickpockets;
    }

    public void setPublicTransport(int publicTransport) {
        this.publicTransport = publicTransport;
    }

    public void setShops(int shops) {
        this.shops = shops;
    }

    public void setTraffic(int traffic) {
        this.traffic = traffic;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLongT(double longT) {
        this.longT = longT;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
