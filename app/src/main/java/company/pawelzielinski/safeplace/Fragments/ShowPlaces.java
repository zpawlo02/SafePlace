package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import company.pawelzielinski.safeplace.Adapters.PlacesListAdapter;
import company.pawelzielinski.safeplace.Classes.EditTextV2;
import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.MainMenu;
import company.pawelzielinski.safeplace.R;


public class ShowPlaces extends Fragment {

    private View view;
    private ListView listView;
    private EditTextV2 editTextCity;
    private RadioButton radioButtonAll, radioButtonSafe, radioButtonNotSafe;
    private String city = "";
    private int startAtNumber = 1, stopAtNumber = 5, whichPlaces = 1;
    private ArrayList<Place> places = new ArrayList<>();
    private ArrayList<String> placesKeys = new ArrayList<>();
    private Context context;
    private PlacesListAdapter adapter;
    private FirebaseFirestore db;
    private Button buttonSearch;
    public boolean wasOpened = false;


    public ShowPlaces() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_showplace, container, false);

        if(wasOpened){
            whichPlaces = Integer.valueOf(getArguments().getString("radio"));
        }

        listView = (ListView) view.findViewById(R.id.listViewPlaces);

        editTextCity =  view.findViewById(R.id.editTextCity);

        editTextCity.setText("");

        radioButtonAll = (RadioButton) view.findViewById(R.id.radioAllS);
        radioButtonSafe = (RadioButton) view.findViewById(R.id.radioSafeS);
        radioButtonNotSafe = (RadioButton) view.findViewById(R.id.radioNotSafeS);

        buttonSearch = (Button) view.findViewById(R.id.buttonSearch);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls

        if(whichPlaces == 1){
            radioButtonAll.setChecked(true);
        }else if(whichPlaces == 2){
            radioButtonSafe.setChecked(true);
        }else if(whichPlaces == 3){
            radioButtonNotSafe.setChecked(true);
        }
        db = FirebaseFirestore.getInstance();
        db.enableNetwork();
        updatePlaces(savedInstanceState);

        radioButtonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichPlaces = 1;
                db = FirebaseFirestore.getInstance();
                db.enableNetwork();
                updatePlaces(savedInstanceState);
            }
        });

        radioButtonSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichPlaces = 2;
                db = FirebaseFirestore.getInstance();
                db.enableNetwork();
                updatePlaces(savedInstanceState);
            }
        });

        radioButtonNotSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichPlaces = 3;
                db = FirebaseFirestore.getInstance();
                db.enableNetwork();
                updatePlaces(savedInstanceState);
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                db.enableNetwork();
                updatePlaces(savedInstanceState);
                editTextCity.clearFocus();
            }
        });


        //LISTENERS

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == android.view.KeyEvent.KEYCODE_BACK){
                    editTextCity.clearFocus();
                    editTextCity.setText("");
                    db.disableNetwork();
                    getFragmentManager().beginTransaction().remove(ShowPlaces.this).commit();
                    return true;
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putString("key", placesKeys.get(position));
                b.putString("whichFragment", "places");
                b.putString("radio", String.valueOf(whichPlaces));
                ShowItem showItem = new ShowItem();
                showItem.setArguments(b);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.drawer_layout, showItem).addToBackStack(null)
                        .commit();
             }
        });

        // Inflate the layout for this fragment
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }



    //1 - ALL 2 - SAFE 3 - NOT SAFE

private void updatePlaces(final Bundle bundle){

    if(!editTextCity.getText().toString().equals("")){
        city = editTextCity.getText().toString();
    }else {
        city = "";
    }

    places.clear();
    placesKeys.clear();


  //  DocumentReference ref = db.collection("places").document();



   /* database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("place");*/

    //startAt(startAtNumber).endAt(stopAtNumber).
    if (city != "" && whichPlaces == 1) {

        //db.enableNetwork();

        db.collection("places").whereEqualTo("city",city).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if( e != null){

                }

                for(DocumentChange documentChange :queryDocumentSnapshots.getDocumentChanges()){
                    places.add(documentChange.getDocument().toObject(Place.class));
                    placesKeys.add(documentChange.getDocument().getId());
                }

                adapter  = new PlacesListAdapter(context, R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

      //  db.disableNetwork();


    } else if (city != "" && whichPlaces == 2) {

      //  db.enableNetwork();

        db.collection("places").whereEqualTo("city",city).whereEqualTo("isSafe",true).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if( e != null){

                }

                for(DocumentChange documentChange :queryDocumentSnapshots.getDocumentChanges()){
                    places.add(documentChange.getDocument().toObject(Place.class));
                    placesKeys.add(documentChange.getDocument().getId());
                }

                adapter  = new PlacesListAdapter(context, R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

      //  db.disableNetwork();

    } else if (city != "" && whichPlaces == 3) {

      //  db.enableNetwork();

        db.collection("places").whereEqualTo("city",city).whereEqualTo("isSafe",false).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if( e != null){

                }

                for(DocumentChange documentChange :queryDocumentSnapshots.getDocumentChanges()){
                    places.add(documentChange.getDocument().toObject(Place.class));
                    placesKeys.add(documentChange.getDocument().getId());
                }

                adapter  = new PlacesListAdapter(context, R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
      //  db.disableNetwork();

    } else if (city.equals("") && whichPlaces == 1) {

        //db.enableNetwork();

        db.collection("places").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if( e != null){

                }

                for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
                    places.add(documentChange.getDocument().toObject(Place.class));
                    placesKeys.add(documentChange.getDocument().getId());
                }

                adapter  = new PlacesListAdapter(context, R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
       // db.disableNetwork();


    } else if (city.equals("") && whichPlaces == 2) {

       // db.enableNetwork();

        db.collection("places").whereEqualTo("isSafe", true).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if( e != null){

                }

                for(DocumentChange documentChange :queryDocumentSnapshots.getDocumentChanges()){
                    places.add(documentChange.getDocument().toObject(Place.class));
                    placesKeys.add(documentChange.getDocument().getId());
                }

                adapter  = new PlacesListAdapter(context, R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

     //   db.disableNetwork();


    } else if (city.equals("") && whichPlaces == 3) {

      //  db.enableNetwork();

        db.collection("places").whereEqualTo("isSafe", false).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if( e != null){

                }

                for(DocumentChange documentChange :queryDocumentSnapshots.getDocumentChanges()){
                    places.add(documentChange.getDocument().toObject(Place.class));
                    placesKeys.add(documentChange.getDocument().getId());
                }

                adapter  = new PlacesListAdapter(context, R.layout.adapter_view_layout, places, bundle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
     //   db.disableNetwork();


    }

}

}
