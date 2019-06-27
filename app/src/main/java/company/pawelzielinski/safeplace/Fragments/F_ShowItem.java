package company.pawelzielinski.safeplace.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompatExtras;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import company.pawelzielinski.safeplace.Adapters.CommentsListAdapter;
import company.pawelzielinski.safeplace.Adapters.PlacesListAdapter;
import company.pawelzielinski.safeplace.Classes.Comment;
import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.Classes.Rating;
import company.pawelzielinski.safeplace.R;


public class F_ShowItem extends Fragment {

    private TextView textViewTraffic, textViewPick, textViewKidnapping, textViewHomeless,
    textViewPublic, textViewParties, textViewShops, textViewCar, textViewKids, textViewCom,
    textViewSafeNot, textViewCountryCity, textViewRating;
    private Button buttonAddComment;
    private EditText editTextComment;
    private String key;
    private Place p;
    private CircleOptions circleOptions;
    private MapView mapView;
    private ArrayList<Comment> arrayListComments = new ArrayList<>();
    private CommentsListAdapter adapter;
    private ListView listViewComments;
    private ArrayList<Integer> arrayListRatings;

    public F_ShowItem() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

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
        textViewSafeNot = (TextView) v.findViewById(R.id.textViewSafeNotSafeItemi);
        textViewCountryCity = (TextView) v.findViewById(R.id.textViewCountryCityi);
        textViewRating = (TextView) v.findViewById(R.id.textViewRatingNumberi);
        mapView = (MapView) v.findViewById(R.id.imageViewMapShowi);
        buttonAddComment = (Button) v.findViewById(R.id.buttonAddComment);
        editTextComment = (EditText) v.findViewById(R.id.editTextAddComment);
        listViewComments = (ListView) v.findViewById(R.id.listViewComments);

        key = getArguments().getString("key");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
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
                textViewCom.setText("  " + p.getComment());

                if(p.getisSafe() == true){
                    textViewSafeNot.setText("Safe");
                }else {
                    textViewSafeNot.setText("Not safe");
                }
                textViewRating.setText(String.valueOf(p.getRating()));
                textViewCountryCity.setText(p.getCountry() + " " + p.getCity());

                LatLng latLng = new LatLng(p.getLat(), p.getLongT());
                if (!p.getisSafe()) {
                    circleOptions = new CircleOptions()
                            .strokeWidth(4)
                            .radius(p.getCircleRadius())
                            .center(latLng)
                            .strokeColor(Color.parseColor("#490033"))
                            .fillColor(Color.argb(50, 230, 0, 0));
                } else {

                    circleOptions = new CircleOptions()
                            .strokeWidth(4)
                            .radius(p.getCircleRadius())
                            .center(latLng)
                            .strokeColor(Color.parseColor("#490033"))
                            .fillColor(Color.argb(50, 0, 230, 0));

                }

                MapsInitializer.initialize(getContext());

                mapView.onCreate(savedInstanceState);
                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        GoogleMap map = googleMap;
                        if (circleOptions.getRadius() <= 500) {
                            map.animateCamera(CameraUpdateFactory.zoomTo(16.4f));
                        } else if (circleOptions.getRadius() <= 700) {
                            map.animateCamera(CameraUpdateFactory.zoomTo(15.4f));
                        } else if (circleOptions.getRadius() <= 900) {
                            map.animateCamera(CameraUpdateFactory.zoomTo(14.4f));
                        }
                        Circle circle1 = map.addCircle(circleOptions);
                        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(p.getLat(), p.getLongT())));
                        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {

                            }
                        });
                    }

                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loadComments(database,savedInstanceState);
        loadRating(database);

        listViewComments.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        textViewRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("key", key);
                F_RatePlace f_ratePlace = new F_RatePlace();
                f_ratePlace.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.drawer_layout,f_ratePlace).addToBackStack(null).commit();
            }
        });

        buttonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextComment.getText().toString().equals("")){

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("comments").child(key).push();
                    mDatabase.setValue(new Comment(editTextComment.getText().toString(),user.getUid(), user.getDisplayName()));
                    loadComments(database,savedInstanceState);
                }
            }
        });


        return v;
    }

    private void loadComments(FirebaseDatabase db, final Bundle bundle){

        arrayListComments.clear();

        db = FirebaseDatabase.getInstance();

        DatabaseReference ref = db.getReference("comments").child(key);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    arrayListComments.add(snapshot.getValue(Comment.class));
                }

                adapter = new CommentsListAdapter(getContext(), R.layout.adapter_comment_view_layout, arrayListComments, bundle);
                listViewComments.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void loadRating(FirebaseDatabase database){

        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("ratings").child(key);



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double rate = 0.0;
                Integer divider = 0;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    divider++;
                    rate += snapshot.getValue(Rating.class).getRating();
                }

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("place").child(key).child("rating");
                ref.setValue(Math.round(((rate/divider) * 10.0))/10.0);
                textViewRating.setText(String.valueOf(Math.round(((rate/divider) * 10.0))/10.0));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
