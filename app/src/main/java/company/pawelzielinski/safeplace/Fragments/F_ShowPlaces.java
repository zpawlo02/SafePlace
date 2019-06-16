package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.firebase.database.Query;
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
    private EditText editTextCity;
    private RadioButton radioButtonAll, radioButtonSafe, radioButtonNotSafe;
    private FirebaseDatabase database;
    private String city = "";
    private int startAtNumber = 1, stopAtNumber = 5, whichPlaces = 1;
    private ArrayList<Place> places = new ArrayList<>();
    private PlacesListAdapter adapter;


    public F_ShowPlaces() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_f__showplace, container, false);

        listView = (ListView) view.findViewById(R.id.listViewPlaces);

        editTextCity = (EditText) view.findViewById(R.id.editTextCity);
        editTextCity.setText("");

        radioButtonAll = (RadioButton) view.findViewById(R.id.radioAllS);
        radioButtonSafe = (RadioButton) view.findViewById(R.id.radioSafeS);
        radioButtonNotSafe = (RadioButton) view.findViewById(R.id.radioNotSafeS);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls

        updatePlaces(savedInstanceState);

        radioButtonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichPlaces = 1;
                updatePlaces(savedInstanceState);
            }
        });

        radioButtonSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichPlaces = 2;
                updatePlaces(savedInstanceState);
            }
        });

        radioButtonNotSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichPlaces = 3;
                updatePlaces(savedInstanceState);
            }
        });

        editTextCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updatePlaces(savedInstanceState);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //LISTENERS

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
//1 - ALL 2 - SAFE 3 - NOT SAFE

private void updatePlaces(final Bundle bundle){

    if(!editTextCity.getText().toString().equals("")){
        city = editTextCity.getText().toString();
    }else {
        city = "";
    }

    places.clear();

    database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("place");

    //startAt(startAtNumber).endAt(stopAtNumber).
    if (city != "" && whichPlaces == 1) {

        ref.orderByChild("city").equalTo(city).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.i("DUGBA", snapshot.getValue(Place.class).getAdress());
                    places.add(snapshot.getValue(Place.class));

                }
                adapter  = new PlacesListAdapter(getContext(), R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    } else if (city != "" && whichPlaces == 2) {
        ref.orderByChild("city").equalTo(city).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.getValue(Place.class).getisSafe()){
                        places.add(snapshot.getValue(Place.class));
                    }

                }
                adapter  = new PlacesListAdapter(getContext(), R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    } else if (city != "" && whichPlaces == 3) {
        ref.orderByChild("city").equalTo(city).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(!snapshot.getValue(Place.class).getisSafe()){
                        places.add(snapshot.getValue(Place.class));
                    }

                }
                adapter  = new PlacesListAdapter(getContext(), R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    } else if (city.equals("") && whichPlaces == 1) {

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    places.add(snapshot.getValue(Place.class));

                }
                adapter  = new PlacesListAdapter(getContext(), R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    } else if (city.equals("") && whichPlaces == 2) {
        ref.orderByChild("isSafe").equalTo(true).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    places.add(snapshot.getValue(Place.class));

                }
                adapter  = new PlacesListAdapter(getContext(), R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    } else if (city.equals("") && whichPlaces == 3) {
        ref.orderByChild("isSafe").equalTo(false).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    places.add(snapshot.getValue(Place.class));

                }
                adapter  = new PlacesListAdapter(getContext(), R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    // places = downloadPlaces(whichPlace,city,query);




}

}
