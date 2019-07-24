package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import company.pawelzielinski.safeplace.Classes.EditTextV2;
import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.MapsActivity;
import company.pawelzielinski.safeplace.R;


public class EditPlace extends Fragment {

    private View view;
    private RadioButton radioButtonSafe, radioButtonNotSafe;
    private EditTextV2 editTextComment;

    private Boolean isSafe = true;
    public  Boolean wasOpened = false, saved = false;
    private String comment;
    private String key, country, city, adress;
    private Context context;


    //CIRCLE
    private LatLng mCircleCenter;
    private Double lat, longt;
    private int circleRadius = 250;

    private int traffic = 1, pickpockets = 1, kidnapping = 1, homeless = 1, publicTransport = 1,
            parties = 1, shops = 1, carthefts = 1, kids = 1;
    //Increase
    private Button iTraffic, iPickpockets, iKidnapping, iHomeless,
            iPublicTransport, iParties, iShops, iCarthefts, iKids;

    //Decrease
    private Button dTraffic, dPickpockets, dKidnapping, dHomeless,
            dPublicTransport, dParties, dShops, dCarthefts, dKids;

    private Button buttonAddToDB, buttonDelete, buttonOpenMaps;

    //PosobilityRating
    private TextView textTraffic, textPickPocekets, textKidnapping, textHomeless,
            textPublicTransport, textParties, textShops, textCarthefts, textKids;

    private Place place;
    public EditPlace() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_place, container, false);

        //BUTTONS

        buttonOpenMaps = (Button) view.findViewById(R.id.buttonOpenMapsE);
        buttonDelete = (Button) view.findViewById(R.id.buttonDeletePlace);
        buttonAddToDB = (Button) view.findViewById(R.id.buttonAddPlaceToDBE);

        iTraffic = (Button) view.findViewById(R.id.buttonIncreaseTrafficE);
        iPickpockets = (Button) view.findViewById(R.id.buttonIncreasePickpocketsE);
        iHomeless = (Button) view.findViewById(R.id.buttonIncreaseHomelessE);
        iKidnapping = (Button) view.findViewById(R.id.buttonIncreaseKidnappingE);
        iPublicTransport = (Button) view.findViewById(R.id.buttonIncreasePublicTransportE);
        iParties = (Button) view.findViewById(R.id.buttonIncreasePartiesE);
        iShops = (Button) view.findViewById(R.id.buttonIncreaseShopsE);
        iCarthefts = (Button) view.findViewById(R.id.buttonIncreaseCarTheftsE);
        iKids = (Button) view.findViewById(R.id.buttonIncreaseKidsE);

        dTraffic = (Button) view.findViewById(R.id.buttonDecreaseTrafficE);
        dPickpockets = (Button) view.findViewById(R.id.buttonDecreasePickpocketsE);
        dHomeless = (Button) view.findViewById(R.id.buttonDecreaseHomelessE);
        dKidnapping = (Button) view.findViewById(R.id.buttonDecreaseKidnappingE);
        dPublicTransport = (Button) view.findViewById(R.id.buttonDecreasePublicTransportE);
        dParties = (Button) view.findViewById(R.id.buttonDecreasePartiesE);
        dShops = (Button) view.findViewById(R.id.buttonDecreaseShopsE);
        dCarthefts = (Button) view.findViewById(R.id.buttonDecreaseCarTheftsE);
        dKids = (Button) view.findViewById(R.id.buttonDecreaseKidsE);

        textTraffic = (TextView) view.findViewById(R.id.textViewTrafficE);
        textPickPocekets = (TextView) view.findViewById(R.id.textViewPickpocketscE);
        textHomeless = (TextView) view.findViewById(R.id.textViewHomelessE);
        textKidnapping = (TextView) view.findViewById(R.id.textViewKidnappingE);
        textPublicTransport = (TextView) view.findViewById(R.id.textViewPublicTransportE);
        textParties = (TextView) view.findViewById(R.id.textViewPartiesE);
        textShops = (TextView) view.findViewById(R.id.textViewShopsE);
        textCarthefts = (TextView) view.findViewById(R.id.textViewCarTheftsE);
        textKids= (TextView) view.findViewById(R.id.textViewKidsE);

        editTextComment = view.findViewById(R.id.editCommentE);
        //*****************************************************************************


        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        if( wasOpened == true){
            isSafe = getArguments().getBoolean("isSafe", true);
            traffic =  getArguments().getInt("traffic", 1);
            pickpockets =  getArguments().getInt("pickpockets", 1);
            kidnapping =  getArguments().getInt("kidnapping", 1);
            homeless =  getArguments().getInt("homeless", 1);
            publicTransport =  getArguments().getInt("publicTransport", 1);
            parties =  getArguments().getInt("parties", 1);
            shops =  getArguments().getInt("shops", 1);
            carthefts =  getArguments().getInt("carthefts", 1);
            kids =  getArguments().getInt("kids", 1);
            circleRadius = getArguments().getInt("circleRadius",1);
            comment = getArguments().getString("comment","");
            lat = getArguments().getDouble("latitude",1);
            longt = getArguments().getDouble("longitude",1);
            mCircleCenter = new LatLng(lat,longt);
            key = getArguments().getString("key");

            textTraffic.setText(String.valueOf(traffic));
            textPickPocekets.setText(String.valueOf(pickpockets));
            textHomeless.setText(String.valueOf(homeless));
            textKidnapping.setText(String.valueOf(kidnapping));
            textPublicTransport.setText(String.valueOf(publicTransport));
            textParties.setText(String.valueOf(parties));
            textShops.setText(String.valueOf(shops));
            textCarthefts.setText(String.valueOf(carthefts));
            textKids.setText(String.valueOf(kids));
            editTextComment.setText(comment);

        }else {
            key = getArguments().getString("key");

            db.collection("places").document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot d = task.getResult();
                    place = d.toObject(Place.class);

                    textTraffic.setText(String.valueOf(place.getTraffic()));
                    traffic = place.getTraffic();
                    textPublicTransport.setText(String.valueOf(place.getPublicTransport()));
                    publicTransport = place.getPublicTransport();
                    textShops.setText(String.valueOf(place.getShops()));
                    shops = place.getShops();
                    textPickPocekets.setText(String.valueOf(place.getPickpockets()));
                    pickpockets = place.getPickpockets();
                    textKids.setText(String.valueOf(place.getKids()));
                    kids = place.getKids();
                    textParties.setText(String.valueOf(place.getParties()));
                    parties = place.getParties();
                    textKidnapping.setText(String.valueOf(place.getKidnapping()));
                    kidnapping = place.getKidnapping();
                    textHomeless.setText(String.valueOf(place.getHomeless()));
                    homeless = place.getHomeless();
                    textCarthefts.setText(String.valueOf(place.getCarthefts()));
                    carthefts = place.getCarthefts();
                    editTextComment.setText("  " + place.getComment());
                    lat = place.getLat();
                    longt= place.getLongT();

                    if(place.getisSafe() == true){
                        isSafe = true;
                        radioButtonSafe.setChecked(true);
                        radioButtonNotSafe.setChecked(false);
                    }else {
                        isSafe = false;
                        radioButtonNotSafe.setChecked(true);
                        radioButtonSafe.setChecked(false);
                    }

                }
            });
        }

        //*****************************************************************************

        //RADIOBUTTONS
        radioButtonSafe = (RadioButton) view.findViewById(R.id.radioSafeE);
        radioButtonNotSafe = (RadioButton) view.findViewById(R.id.radioNotSafeE);

        // Inflate the layout for this fragment


        //LISTENERS

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == android.view.KeyEvent.KEYCODE_BACK){
                    editTextComment.clearFocus();
                    editTextComment.setText("");
                    Bundle b = new Bundle();
                    b.putString("key", key);
                    MyPlaces f_myPlacees = new MyPlaces();

                    f_myPlacees.setArguments(b);

                    if(wasOpened == true || saved == true){
                        f_myPlacees.wasOpened = true;
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.fMaps, f_myPlacees)
                                .commit();
                    }else {
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.drawer_layout, f_myPlacees)
                                .commit();
                    }

                    getFragmentManager().beginTransaction().remove(EditPlace.this).commit();
                    return true;
                }
                return false;
            }
        });

        buttonOpenMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = editTextComment.getText().toString();
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("traffic", traffic);
                intent.putExtra("pickpockets", pickpockets);
                intent.putExtra("kidnapping", kidnapping);
                intent.putExtra("homeless", homeless);
                intent.putExtra("publicTransport", publicTransport);
                intent.putExtra("parties", parties);
                intent.putExtra("shops", shops);
                intent.putExtra("carthefts", carthefts);
                intent.putExtra("kids", kids);
                intent.putExtra("comment", comment);
                intent.putExtra("isSafe", isSafe);
                intent.putExtra("circleRadius",circleRadius);
                intent.putExtra("whichF", "edit");
                intent.putExtra("lat", lat);
                intent.putExtra("long", longt);
                intent.putExtra("circle", circleRadius);
                intent.putExtra("key", key);
                getActivity().finish();
                getActivity().startActivity(intent);


            }
        });


        buttonAddToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = editTextComment.getText().toString();
                editPlace(place,key);
                MyPlaces f_myPlacees = new MyPlaces();


                if(wasOpened == true && saved){
                    f_myPlacees.wasOpened = true;
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fMaps, f_myPlacees)
                            .addToBackStack(null).commit();
                    getFragmentManager().beginTransaction().remove(EditPlace.this).commit();
                }else {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.drawer_layout, f_myPlacees)
                            .addToBackStack(null).commit();
                    getFragmentManager().beginTransaction().remove(EditPlace.this).commit();
                }

            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.collection("places").document(key).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        MyPlaces f_myPlacees = new MyPlaces();

                        if(wasOpened == true && saved){
                            f_myPlacees.wasOpened = true;
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fMaps, f_myPlacees)
                                    .addToBackStack(null).commit();
                            getFragmentManager().beginTransaction().remove(EditPlace.this).commit();
                        }else {
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.drawer_layout, f_myPlacees)
                                    .addToBackStack(null).commit();
                            getFragmentManager().beginTransaction().remove(EditPlace.this).commit();
                        }
                    }
                });
            }
        });

        radioButtonSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSafe = true;
            }
        });

        radioButtonNotSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSafe = false;
            }
        });


        iTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true,textTraffic);
                traffic = Integer.parseInt(textTraffic.getText().toString());
            }
        });

        iPickpockets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textPickPocekets);
                pickpockets = Integer.parseInt(textPickPocekets.getText().toString());
            }
        });

        iHomeless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textHomeless);
                homeless = Integer.parseInt(textHomeless.getText().toString());
            }
        });

        iKidnapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textKidnapping);
                kidnapping = Integer.parseInt(textKidnapping.getText().toString());
            }
        });

        iPublicTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textPublicTransport);
                publicTransport = Integer.parseInt(textPublicTransport.getText().toString());
            }
        });

        iParties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textParties);
                parties = Integer.parseInt(textParties.getText().toString());
            }
        });

        iShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textShops);
                shops = Integer.parseInt(textShops.getText().toString());
            }
        });

        iCarthefts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textCarthefts);
                carthefts = Integer.parseInt(textCarthefts.getText().toString());
            }
        });


        iKids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textKids);
                kids = Integer.parseInt(textKids.getText().toString());
            }
        });

        //*****************************************************************************

        dTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false,textTraffic);
                traffic = Integer.parseInt(textTraffic.getText().toString());
            }
        });

        dPickpockets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textPickPocekets);
                pickpockets = Integer.parseInt(textPickPocekets.getText().toString());
            }
        });

        dHomeless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textHomeless);
                homeless = Integer.parseInt(textHomeless.getText().toString());
            }
        });

        dKidnapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textKidnapping);
                kidnapping = Integer.parseInt(textKidnapping.getText().toString());
            }
        });

        dPublicTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textPublicTransport);
                publicTransport = Integer.parseInt(textPublicTransport.getText().toString());
            }
        });

        dParties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textParties);
                parties = Integer.parseInt(textParties.getText().toString());

            }
        });

        dShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textShops);
                shops = Integer.parseInt(textShops.getText().toString());
            }
        });

        dCarthefts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textCarthefts);
                carthefts = Integer.parseInt(textCarthefts.getText().toString());
            }
        });

        dKids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textKids);
                kids = Integer.parseInt(textKids.getText().toString());
            }
        });

        //*****************************************************************************


        if(isSafe == false){
            radioButtonNotSafe.setChecked(true);
            radioButtonSafe.setChecked(false);
        }
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    private void changePosibility(boolean increase, TextView which){
        if(increase == true && Integer.parseInt(which.getText().toString()) < 5){
            if(which == textTraffic){
                textTraffic.setText(String.valueOf(Integer.parseInt(textTraffic.getText().toString())+1));
            }else if (which == textPickPocekets){
                textPickPocekets.setText(String.valueOf(Integer.parseInt(textPickPocekets.getText().toString())+1));
            }else if (which == textHomeless){
                textHomeless.setText(String.valueOf(Integer.parseInt(textHomeless.getText().toString())+1));
            }else if (which == textKidnapping){
                textKidnapping.setText(String.valueOf(Integer.parseInt(textKidnapping.getText().toString())+1));
            }else if (which == textPublicTransport){
                textPublicTransport.setText(String.valueOf(Integer.parseInt(textPublicTransport.getText().toString())+1));
            }else if (which == textParties){
                textParties.setText(String.valueOf(Integer.parseInt(textParties.getText().toString())+1));
            }else if (which == textShops){
                textShops.setText(String.valueOf(Integer.parseInt(textShops.getText().toString())+1));
            }else if (which == textCarthefts){
                textCarthefts.setText(String.valueOf(Integer.parseInt(textCarthefts.getText().toString())+1));
            }else if (which == textKids){
                textKids.setText(String.valueOf(Integer.parseInt(textKids.getText().toString())+1));
            }
        }else if(Integer.parseInt(which.getText().toString()) > 1 && increase == false) {
            if(which == textTraffic){
                textTraffic.setText(String.valueOf(Integer.parseInt(textTraffic.getText().toString())-1));
            }else if (which == textPickPocekets){
                textPickPocekets.setText(String.valueOf(Integer.parseInt(textPickPocekets.getText().toString())-1));
            }else if (which == textHomeless){
                textHomeless.setText(String.valueOf(Integer.parseInt(textHomeless.getText().toString())-1));
            }else if (which == textKidnapping){
                textKidnapping.setText(String.valueOf(Integer.parseInt(textKidnapping.getText().toString())-1));
            }else if (which == textPublicTransport){
                textPublicTransport.setText(String.valueOf(Integer.parseInt(textPublicTransport.getText().toString())-1));
            }else if (which == textParties){
                textParties.setText(String.valueOf(Integer.parseInt(textParties.getText().toString())-1));
            }else if (which == textShops){
                textShops.setText(String.valueOf(Integer.parseInt(textShops.getText().toString())-1));
            }else if (which == textCarthefts){
                textCarthefts.setText(String.valueOf(Integer.parseInt(textCarthefts.getText().toString())-1));
            }else if (which == textKids){
                textKids.setText(String.valueOf(Integer.parseInt(textKids.getText().toString())-1));
            }
        }
    }

    private void editPlace(Place place, String key){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> adresses = null;

        try {

            adresses = geocoder.getFromLocation(lat,longt,1);
        }
        catch (IOException e){
        }
        country = adresses.get(0).getCountryName();
        city = adresses.get(0).getLocality();
        adress = adresses.get(0).getAddressLine(0);

        db.collection("places").document(key).update("kidnapping", kidnapping);
        db.collection("places").document(key).update("kids", kids);
        db.collection("places").document(key).update("isSafe", isSafe);
        db.collection("places").document(key).update("parties", parties);
        db.collection("places").document(key).update("publicTransport", publicTransport);
        db.collection("places").document(key).update("traffic", traffic);
        db.collection("places").document(key).update("shops", shops);
        db.collection("places").document(key).update("homeless", homeless);
        db.collection("places").document(key).update("carthefts", carthefts);
        db.collection("places").document(key).update("pickpockets", pickpockets);
        db.collection("places").document(key).update("comment", editTextComment.getText().toString());
        db.collection("places").document(key).update("lat", lat);
        db.collection("places").document(key).update("longT", longt);
        db.collection("places").document(key).update("circleRadius", circleRadius);
        db.collection("places").document(key).update("country", country);
        db.collection("places").document(key).update("city", city);
        db.collection("places").document(key).update("adress", adress);
    }

}

