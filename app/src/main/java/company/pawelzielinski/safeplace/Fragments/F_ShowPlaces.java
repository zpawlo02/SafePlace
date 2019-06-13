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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private int startAtNumber = 1, stopAtNumber = 5;


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

        editTextCountry = (EditText) view.findViewById(R.id.editTextCountry);
        editTextCity = (EditText) view.findViewById(R.id.editTextCity);

        radioButtonAll = (RadioButton) view.findViewById(R.id.radioAllS);
        radioButtonSafe = (RadioButton) view.findViewById(R.id.radioSafeS);
        radioButtonNotSafe = (RadioButton) view.findViewById(R.id.radioNotSafeS);

        //will be containing places
        final ArrayList<Place> places = new ArrayList<>();

        final PlacesListAdapter adapter = new PlacesListAdapter(getContext(), R.layout.adapter_view_layout, places);
        listView.setAdapter(adapter);

        //LISTENERS
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("place");
                //.orderByChild("place") startAt(startAtNumber).endAt(stopAtNumber).
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Context context = getContext();
                            Place p = snapshot.getValue(Place.class);
                            places.add(p);
                            adapter.notifyDataSetChanged();

                            Log.i("CHUUUUJ", "nie laduje SIEE");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        // Inflate the layout for this fragment
        return view;
    }



}
