package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.R;


public class F_ShowItem extends Fragment {

    private TextView textViewTraffic, textViewPick, textViewKidnapping, textViewHomeless,
    textViewPublic, textViewParties, textViewShops, textViewCar, textViewKids, textViewCom;
    String key;
    Place p;

    public F_ShowItem() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_f__show_item, container, false);

        textViewTraffic = (TextView) v.findViewById(R.id.textViewTraffici);
        textViewPublic = (TextView) v.findViewById(R.id.textViewPublicTransporti);
        textViewShops = (TextView) v.findViewById(R.id.textViewShopsi);
        textViewPick = (TextView) v.findViewById(R.id.textViewPickpocketsci);
        textViewKids = (TextView) v.findViewById(R.id.textViewKidsi);
        textViewParties = (TextView) v.findViewById(R.id.textViewPartiesi);
        textViewKidnapping = (TextView) v.findViewById(R.id.textViewKidnappingi);
        textViewHomeless = (TextView) v.findViewById(R.id.textViewHomelessi);
        textViewCar = (TextView) v.findViewById(R.id.textViewCarTheftsi);
        textViewCom = (TextView) v.findViewById(R.id.textViewCommenti);


        key = getArguments().getString("key");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("place").child(key);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                p = dataSnapshot.getValue(Place.class);

                textViewTraffic.setText(String.valueOf(p.getTraffic()));
                textViewPublic.setText(String.valueOf(p.getPublicTransport()));
                textViewShops.setText(String.valueOf(p.getShops()));
                textViewPick.setText(String.valueOf(p.getPickpockets()));
                textViewKids.setText(String.valueOf(p.getKids()));
                textViewParties.setText(String.valueOf(p.getParties()));
                textViewKidnapping.setText(String.valueOf(p.getKidnapping()));
                textViewHomeless.setText(String.valueOf(p.getHomeless()));
                textViewCar.setText(String.valueOf(p.getCarthefts()));
                textViewCom.setText(p.getComment());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return v;
    }



}