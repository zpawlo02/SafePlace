package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import company.pawelzielinski.safeplace.Adapters.PlacesListAdapter;
import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.R;

public class MyPlaces extends Fragment {

    private View view;
    private ListView listView;
    private ArrayList<Place> places = new ArrayList<>();
    private ArrayList<String> placesKeys = new ArrayList<>();
    private PlacesListAdapter adapter;
    private Context context;

    public MyPlaces() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_places, container, false);

        listView = (ListView) view.findViewById(R.id.listViewMyPlaces);

        updatePlaces(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putString("key", placesKeys.get(position));
                EditPlace editPlace = new EditPlace();
                editPlace.setArguments(b);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.drawer_layout, editPlace)
                        .addToBackStack(null).commit();
                getFragmentManager().beginTransaction().remove(MyPlaces.this).commit();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void updatePlaces(final Bundle bundle) {

        places.clear();
        placesKeys.clear();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

   /* database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("place");*/

        //startAt(startAtNumber).endAt(stopAtNumber).

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

            db.collection("places").whereEqualTo("userId", user.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
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
