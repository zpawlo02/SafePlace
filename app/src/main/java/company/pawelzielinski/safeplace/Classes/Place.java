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

    private boolean isSafe;

    private int carthefts;
    private int homeless;
    private int kidnapping;
    private int kids;
    private int parties;
    private int pickpockets;
    private int publicTransport;
    private int shops;
    private int traffic;
    private int circleRadius;

    private double lat;
    private double longT;
    private double rating;

    private LatLng latLng;
    private String comment;

    private String placeId;
    private String country;
    private String city;
    private String street;

    public Place(Context context,boolean isSafe, int carthefts, int homeless, int kidnapping,
                 int kids, int parties, int pickpockets,
                 int publicTransport, int shops, int traffic, int circleRadius,
                 double lat, double longT, double rating, LatLng latLng,
                 String comment, String placeId, String country, String city, String street) {

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
        this.latLng = latLng;
        this.comment = comment;
        this.placeId = placeId;

        //MAKE FUNCTION WHICH WILL BE RETURNING STRING COUNTRY/CITY/STREET

        this.country = adresses.get(0).getCountryName();
        this.city = adresses.get(0).getLocality();
        //this.street = adresses.get(0).get
    }

    public boolean isSafe() {
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

    public LatLng getLatLng() {
        return latLng;
    }

    public String getComment() {
        return comment;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
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

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
