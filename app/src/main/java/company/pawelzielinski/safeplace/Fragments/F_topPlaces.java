package company.pawelzielinski.safeplace.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import company.pawelzielinski.safeplace.Adapters.PlacesListAdapter;
import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.R;


public class F_topPlaces extends Fragment {

    private View view;
    private ListView listView;
    private EditText editTextTopCity;
    private RadioButton radioButtonSafe, radioButtonNotSafe;
    private int whichPlaces = 1;
    private String city = "";
    private ArrayList<Place> places = new ArrayList<>();
    private ArrayList<String> placesKeys = new ArrayList<>();
    private PlacesListAdapter adapter;
    private Context context;

    public boolean wasOpened = false;

    public F_topPlaces() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_f_top_places, container, false);

        listView = (ListView) view.findViewById(R.id.listViewTopPlaces);

        if(wasOpened){
            whichPlaces = Integer.valueOf(getArguments().getString("radio"));
        }


        editTextTopCity = (EditText) view.findViewById(R.id.editTextTopCity);
        editTextTopCity.setText("");

        radioButtonSafe = (RadioButton) view.findViewById(R.id.radioTopSafeS);
        radioButtonNotSafe = (RadioButton) view.findViewById(R.id.radioTopNotSafeS);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls

        if (whichPlaces == 1){
            radioButtonSafe.setChecked(true);
        }else {
            radioButtonNotSafe.setChecked(true);
        }

        updatePlaces(savedInstanceState);

        radioButtonSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichPlaces = 1;
                updatePlaces(savedInstanceState);
            }
        });

        radioButtonNotSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichPlaces = 2;
                updatePlaces(savedInstanceState);
            }
        });

        editTextTopCity.addTextChangedListener(new TextWatcher() {
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
                Bundle b = new Bundle();
                b.putString("key", placesKeys.get(position));
                b.putString("whichFragment", "top");
                b.putString("radio", String.valueOf(whichPlaces));
                F_ShowItem f_showItem = new F_ShowItem();
                f_showItem.setArguments(b);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.drawer_layout, f_showItem)
                        .addToBackStack(null).commit();
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == android.view.KeyEvent.KEYCODE_BACK){
                    getFragmentManager().beginTransaction().remove(F_topPlaces.this).commit();
                    return true;
                }
                return false;
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

    //1 SAFE 2 - NOT SAFE

    private void updatePlaces(final Bundle bundle) {

        if (!editTextTopCity.getText().toString().equals("")) {
            city = editTextTopCity.getText().toString();
        } else {
            city = "";
        }

        places.clear();
        placesKeys.clear();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

   /* database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("place");*/

        //startAt(startAtNumber).endAt(stopAtNumber).
    if (city != "" && whichPlaces == 1) {

            db.collection("places").whereEqualTo("city", city).whereEqualTo("isSafe", true).orderBy("rating", Query.Direction.DESCENDING).limit(10).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {

                    }

                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        places.add(documentChange.getDocument().toObject(Place.class));
                        placesKeys.add(documentChange.getDocument().getId());
                    }

                    adapter = new PlacesListAdapter(context, R.layout.adapter_view_layout, places, bundle);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });

        } else if (city != "" && whichPlaces == 2) {

            db.collection("places").whereEqualTo("city", city).whereEqualTo("isSafe", false).orderBy("rating", Query.Direction.DESCENDING).limit(10).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {

                    }

                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        places.add(documentChange.getDocument().toObject(Place.class));
                        placesKeys.add(documentChange.getDocument().getId());
                    }

                    adapter = new PlacesListAdapter(context, R.layout.adapter_view_layout, places, bundle);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });


        }  else if (city.equals("") && whichPlaces == 1) {

            db.collection("places").orderBy("rating", Query.Direction.DESCENDING).whereEqualTo("isSafe", true).limit(10).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {

                    }

                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        places.add(documentChange.getDocument().toObject(Place.class));
                        placesKeys.add(documentChange.getDocument().getId());
                    }

                    adapter = new PlacesListAdapter(context, R.layout.adapter_view_layout, places, bundle);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });


        } else if (city.equals("") && whichPlaces == 2) {


            db.collection("places").orderBy("rating", Query.Direction.DESCENDING).whereEqualTo("isSafe", false).limit(10).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {

                    }

                    for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                        places.add(documentChange.getDocument().toObject(Place.class));
                        placesKeys.add(documentChange.getDocument().getId());
                    }

                    adapter = new PlacesListAdapter(context, R.layout.adapter_view_layout, places, bundle);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });


        }
    }


}
