package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import company.pawelzielinski.safeplace.Classes.Rating;
import company.pawelzielinski.safeplace.R;


public class RatePlace extends Fragment {

    private SeekBar seekBar;
    private TextView textViewRating;
    private Button buttonSubmit;
    private Integer ratingValue = 1;
    private String key;
    private Context context;

    public RatePlace() {
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

        View v = inflater.inflate(R.layout.fragment_rate_place, container, false);

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

                    FirebaseFirestore database = FirebaseFirestore.getInstance();
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();
                    database.collection("ratings").document(key).collection("rate").document().set(new Rating(user.getUid(), ratingValue));

                getActivity().getSupportFragmentManager().popBackStackImmediate();


            }
        });

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                    return true;
                }
                return false;
            }
        });

        // Inflate the layout for this fragment
        return v;
    }


}
