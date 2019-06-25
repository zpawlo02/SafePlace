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
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import company.pawelzielinski.safeplace.Adapters.CommentsListAdapter;
import company.pawelzielinski.safeplace.Classes.Comment;
import company.pawelzielinski.safeplace.Classes.Rating;
import company.pawelzielinski.safeplace.R;


public class F_RatePlace extends Fragment {

    private SeekBar seekBar;
    private TextView textViewRating;
    private Button buttonSubmit;
    private int ratingValue = 1;
    private String key;

    public F_RatePlace() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_f__rate_place, container, false);

        key = getArguments().getString("key");

        seekBar = (SeekBar) v.findViewById(R.id.seekBarRating);
        textViewRating = (TextView) v.findViewById(R.id.textViewSeekBarRating);
        buttonSubmit = (Button) v.findViewById(R.id.buttonSubmitRating);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ratingValue = progress;
                textViewRating.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ratings").child(key).push();
                mDatabase.setValue(new Rating(user.getUid(),ratingValue));

            }
        });

        // Inflate the layout for this fragment
        return v;
    }



}
