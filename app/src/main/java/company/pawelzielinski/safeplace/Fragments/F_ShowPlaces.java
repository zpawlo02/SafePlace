package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.ref.Reference;
import java.util.ArrayList;

import company.pawelzielinski.safeplace.Adapters.PlacesListAdapter;
import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.R;


public class F_ShowPlaces extends Fragment {

    private View view;
    private ListView listView;
    private Button buttonSubmit;
    private EditText editTextCountry, editTextCity;
    private RadioButton radioButtonAll, radioButtonSafe, radioButtonNotSafe;
    private FirebaseDatabase database;
    private int startAtNumber = 1, stopAtNumber = 5, whichPlace = 1;
    private String city;


    public F_ShowPlaces() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_f__showplace, container, false);

        listView = (ListView) view.findViewById(R.id.listViewPlaces);

        buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);

        editTextCity = (EditText) view.findViewById(R.id.editTextCity);

        radioButtonAll = (RadioButton) view.findViewById(R.id.radioAllS);
        radioButtonSafe = (RadioButton) view.findViewById(R.id.radioSafeS);
        radioButtonNotSafe = (RadioButton) view.findViewById(R.id.radioNotSafeS);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView

        //will be containing places
        final ArrayList<Place> places = new ArrayList<>();

        //LISTENERS
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                city = "";

                if(radioButtonAll.isChecked()){
                    whichPlace = 1;
                }else if(radioButtonSafe.isChecked()){
                    whichPlace = 2;
                }else if(radioButtonNotSafe.isChecked()){
                    whichPlace = 3;
                }

                if(!editTextCity.getText().toString().equals("")){
                    city = editTextCity.getText().toString();
                }

                database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("place");
                //startAt(startAtNumber).endAt(stopAtNumber).

                PlacesListAdapter adapter = new PlacesListAdapter(getContext(), R.layout.adapter_view_layout, downloadPlaces(whichPlace,city,reference));
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                places.clear();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
//1 - ALL 2 - SAFE 3 - NOT SAFE
private ArrayList<Place> downloadPlaces(int whichPlaces, String city, DatabaseReference ref) {
    final ArrayList<Place> places = new ArrayList<>();
    if (city != "" && whichPlaces == 1) {
        ref.orderByChild("city").equalTo(city).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    places.add(snapshot.getValue(Place.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return places;

    } else if (city != "" && whichPlaces == 2) {
        ref.orderByChild("city").equalTo(city).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.getValue(Place.class).getisSafe() == true){
                        places.add(snapshot.getValue(Place.class));
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return places;

    } else if (city != "" && whichPlaces == 3) {
        ref.orderByChild("city").equalTo(city).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.getValue(Place.class).getisSafe() == false){
                        places.add(snapshot.getValue(Place.class));
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return places;

    } else if (city.equals("") && whichPlaces == 1) {
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    places.add(snapshot.getValue(Place.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return places;

    } else if (city.equals("") && whichPlaces == 2) {
        ref.orderByChild("isSafe").equalTo(true).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    places.add(snapshot.getValue(Place.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return places;

    } else if (city.equals("") && whichPlaces == 3) {
        ref.orderByChild("isSafe").equalTo(false).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    places.add(snapshot.getValue(Place.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return places;


    }
    return places;
}

}
