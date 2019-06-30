package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.MapsActivity;
import company.pawelzielinski.safeplace.R;


public class F_EditPlace extends Fragment {

    private View view;
    private RadioButton radioButtonSafe, radioButtonNotSafe;
    private EditText editTextComment;

    private Boolean isSafe = true, mapWasOpened = false;
    private String comment;
    private String key;


    //CIRCLE
    private LatLng mCircleCenter;
    private Double lat = 1.0, longt = 1.0;
    private int circleRadius = 250;

    private int traffic = 1, pickpockets = 1, kidnapping = 1, homeless = 1, publicTransport = 1,
            parties = 1, shops = 1, carthefts = 1, kids = 1;
    //Increase
    private Button iTraffic, iPickpockets, iKidnapping, iHomeless,
            iPublicTransport, iParties, iShops, iCarthefts, iKids;

    //Decrease
    private Button dTraffic, dPickpockets, dKidnapping, dHomeless,
            dPublicTransport, dParties, dShops, dCarthefts, dKids;

    private Button buttonAddToDB;

    //PosobilityRating
    private TextView textTraffic, textPickPocekets, textKidnapping, textHomeless,
            textPublicTransport, textParties, textShops, textCarthefts, textKids;

    private Place place;
    public F_EditPlace() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_f__edit_place, container, false);

        //BUTTONS
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

        //*****************************************************************************

        //RADIOBUTTONS
        radioButtonSafe = (RadioButton) view.findViewById(R.id.radioSafeE);
        radioButtonNotSafe = (RadioButton) view.findViewById(R.id.radioNotSafeE);

        //EDITTEXT
        editTextComment = (EditText) view.findViewById(R.id.editCommentE);

        // Inflate the layout for this fragment

        key = getArguments().getString("key");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("places").document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot d = task.getResult();
                place = d.toObject(Place.class);

                textTraffic.setText(String.valueOf(place.getTraffic()));
                textPublicTransport.setText(String.valueOf(place.getPublicTransport()));
                textShops.setText(String.valueOf(place.getShops()));
                textPickPocekets.setText(String.valueOf(place.getPickpockets()));
                textKids.setText(String.valueOf(place.getKids()));
                textParties.setText(String.valueOf(place.getParties()));
                textKidnapping.setText(String.valueOf(place.getKidnapping()));
                textHomeless.setText(String.valueOf(place.getHomeless()));
                textCarthefts.setText(String.valueOf(place.getCarthefts()));
                editTextComment.setText("  " + place.getComment());

                if(place.getisSafe() == true){
                    radioButtonSafe.setChecked(true);
                    radioButtonNotSafe.setChecked(false);
                }else {
                    radioButtonNotSafe.setChecked(true);
                    radioButtonSafe.setChecked(false);
                }

            }
        });



        //LISTENERS


        buttonAddToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LAT LONG", String.valueOf(lat + " --" + longt + " ----" + circleRadius));
                comment = editTextComment.getText().toString();
                editPlace(place,key);
                getActivity().onBackPressed();
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
      //  db.collection("places").document(key).
    }
/*textTraffic.setText(String.valueOf(place.getTraffic()));
                textPublicTransport.setText(String.valueOf(place.getPublicTransport()));
                textShops.setText(String.valueOf(place.getShops()));
                textPickPocekets.setText(String.valueOf(place.getPickpockets()));
                textKids.setText(String.valueOf(place.getKids()));
                textParties.setText(String.valueOf(place.getParties()));
                textKidnapping.setText(String.valueOf(place.getKidnapping()));
                textHomeless.setText(String.valueOf(place.getHomeless()));
                textCarthefts.setText(String.valueOf(place.getCarthefts()));
                editTextComment.setText("  " + place.getComment());

                if(place.getisSafe() == true){
        radioButtonSafe.setChecked(true);
        radioButtonNotSafe.setChecked(false);
    }else {
        radioButtonNotSafe.setChecked(true);
        radioButtonSafe.setChecked(false);
    }*/

}

