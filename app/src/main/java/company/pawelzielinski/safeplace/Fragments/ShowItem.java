package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import company.pawelzielinski.safeplace.Adapters.CommentsListAdapter;
import company.pawelzielinski.safeplace.Classes.Comment;
import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.Classes.Rating;
import company.pawelzielinski.safeplace.R;


public class ShowItem extends Fragment {

    private TextView textViewTraffic, textViewPick, textViewKidnapping, textViewHomeless,
    textViewPublic, textViewParties, textViewShops, textViewCar, textViewKids, textViewCom,
    textViewSafeNot, textViewCountryCity, textViewRating;
    private Button buttonAddComment;
    private EditText editTextComment;
    private String key, whichFragment, radio;
    private Place p;
    private CircleOptions circleOptions;
    private MapView mapView;
    private ArrayList<Comment> arrayListComments = new ArrayList<>();
    private CommentsListAdapter adapter;
    private ListView listViewComments;
    private Context context;

    public ShowItem() {
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
                             final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_show_item, container, false);

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
        whichFragment = getArguments().getString("whichFragment");
        radio = getArguments().getString("radio");

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == android.view.KeyEvent.KEYCODE_BACK){
                     Bundle b = new Bundle();
                     b.putString("radio", radio);
                    Toast.makeText(context, whichFragment, Toast.LENGTH_SHORT);
                     if(whichFragment.equals("top")){
                         topPlaces f_topPlaces = new topPlaces();
                         f_topPlaces.setArguments(b);
                         f_topPlaces.wasOpened = true;
                         getActivity().getSupportFragmentManager().beginTransaction()
                                 .add(R.id.drawer_layout,f_topPlaces).addToBackStack(null).commit();
                         getFragmentManager().beginTransaction().remove(ShowItem.this).addToBackStack(null).commit();
                        return true;
                    }else if (whichFragment.equals("places")){
                             ShowPlaces f_showPlaces = new ShowPlaces();
                             f_showPlaces.setArguments(b);
                             f_showPlaces.wasOpened = true;
                             getActivity().getSupportFragmentManager().beginTransaction()
                                     .add(R.id.drawer_layout,f_showPlaces).addToBackStack(null).commit();
                             getFragmentManager().beginTransaction().remove(ShowItem.this).addToBackStack(null).commit();
                             return true;

                     }

                }
                return false;
            }
        });

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("places").document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              DocumentSnapshot d = task.getResult();
              p = d.toObject(Place.class);

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
                      if (circleOptions.getRadius() >= 600 && circleOptions.getRadius() < 700) {
                          map.animateCamera(CameraUpdateFactory.zoomTo(14.4f));
                      } else if (circleOptions.getRadius() >= 700 && circleOptions.getRadius() < 800) {
                          map.animateCamera(CameraUpdateFactory.zoomTo(13.4f));
                      } else if (circleOptions.getRadius() >= 800 && circleOptions.getRadius() < 900) {
                          map.animateCamera(CameraUpdateFactory.zoomTo(12.4f));
                      }  else if (circleOptions.getRadius() == 800) {
                          map.animateCamera(CameraUpdateFactory.zoomTo(11.6f));
                      } else if (circleOptions.getRadius() >= 500 && circleOptions.getRadius() < 600) {
                          map.animateCamera(CameraUpdateFactory.zoomTo(15.4f));
                      } else if (circleOptions.getRadius() >= 400 && circleOptions.getRadius() < 500) {
                          map.animateCamera(CameraUpdateFactory.zoomTo(15.0f));
                      } else if (circleOptions.getRadius() >= 300 && circleOptions.getRadius() < 400) {
                          map.animateCamera(CameraUpdateFactory.zoomTo(16.4f));
                      } else if (circleOptions.getRadius() >= 200 && circleOptions.getRadius() < 300) {
                          map.animateCamera(CameraUpdateFactory.zoomTo(15.8f));
                      } else if (circleOptions.getRadius() >= 100 && circleOptions.getRadius() < 200) {
                          map.animateCamera(CameraUpdateFactory.zoomTo(16.0f));
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
      });

       loadComments(db,savedInstanceState);
       loadRating(db);


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
                FirebaseFirestore db = FirebaseFirestore.getInstance();


                db.collection("ratings").document(key).collection("rate").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        FirebaseUser user = auth.getCurrentUser();
                        int counter = 0;

                        for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
                            if(documentChange.getDocument().toObject(Rating.class).getUserId().equals(user.getUid())){
                                counter++;
                            }
                        }

                        if(counter == 0){
                            Bundle b = new Bundle();
                            b.putString("key", key);
                            RatePlace ratePlace = new RatePlace();
                            ratePlace.setArguments(b);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .add(R.id.drawer_layout, ratePlace).addToBackStack(null).commit();

                        }else {

                        }
                    }
                });
            }
        });

        buttonAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextComment.getText().toString().equals("")){

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    db.collection("comments").document(key).collection("com").document().set(new Comment(editTextComment.getText().toString(),user.getUid(), user.getDisplayName()));
                }
            }
        });


        return v;
    }

    private void loadComments(FirebaseFirestore db, final Bundle bundle){

        arrayListComments.clear();

        db.collection("comments").document(key).collection("com").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
                    arrayListComments.add(documentChange.getDocument().toObject(Comment.class));
                }
                adapter = new CommentsListAdapter(context, R.layout.adapter_comment_view_layout, arrayListComments, bundle);
                listViewComments.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void loadRating(final FirebaseFirestore db){



        db.collection("ratings").document(key).collection("rate").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                Double rate = 0.0;
                Integer divider = 0;

                for(DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()){
                    rate += documentChange.getDocument().toObject(Rating.class).getRating();
                    divider++;
                }
                db.collection("places").document(key).update("rating",Math.round(((rate/divider) * 10.0))/10.0);
                textViewRating.setText(String.valueOf(Math.round(((rate/divider) * 10.0))/10.0));
            }
        });

    }



}
