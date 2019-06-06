package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

import company.pawelzielinski.safeplace.MainMenu;
import company.pawelzielinski.safeplace.MapsActivity;
import company.pawelzielinski.safeplace.R;

public class F_ADDPlace extends Fragment {
    public boolean wasOpened = false;
    private View view;
    private Button buttonOpenMaps;
    private RadioButton radioButtonSafe, radioButtonNotSafe;
    private EditText editTextComment;

    private Boolean isSafe = true, mapWasOpened = false;
    private String comment;


    //CIRCLE
    private LatLng mCircleCenter;
    private Double lat, longt;
    private Double circleRadius;

    private int traffic = 1, pickpockets = 1, kidnapping = 1, homeless = 1, publicTransport = 1,
                parties = 1, shops = 1, carthefts = 1;
    //Increase
    private Button iTraffic, iPickpockets, iKidnapping, iHomeless,
            iPublicTransport, iParties, iShops, iCarthefts;

    //Decrease
    private Button dTraffic, dPickpockets, dKidnapping, dHomeless,
            dPublicTransport, dParties, dShops, dCarthefts;

    //PosobilityRating
    private TextView textTraffic, textPickPocekets, textKidnapping, textHomeless,
    textPublicTransport, textParties, textShops, textCarthefts;


    private OnFragmentInteractionListener mListener;

    public F_ADDPlace() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addplace, container, false);

        // Inflate the layout for this fragment

        buttonOpenMaps = (Button) view.findViewById(R.id.buttonOpenMaps);

        buttonOpenMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = editTextComment.getText().toString();
                Intent intent = new Intent(getActivity().getBaseContext(), MapsActivity.class);
                intent.putExtra("traffic", traffic);
                intent.putExtra("pickpockets", pickpockets);
                intent.putExtra("kidnapping", kidnapping);
                intent.putExtra("homeless", homeless);
                intent.putExtra("publicTransport", publicTransport);
                intent.putExtra("parties", parties);
                intent.putExtra("shops", shops);
                intent.putExtra("carthefts", carthefts);
                intent.putExtra("comment", comment);
                intent.putExtra("isSafe", isSafe);
                getActivity().startActivity(intent);
                getFragmentManager().popBackStack();

            }
        });

        radioButtonSafe = (RadioButton) view.findViewById(R.id.radioSafe);
        radioButtonNotSafe = (RadioButton) view.findViewById(R.id.radioNotSafe);

        radioButtonSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSafe = true;
            }
        });

        radioButtonNotSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSafe = false;
            }
        });

        iTraffic = (Button) view.findViewById(R.id.buttonIncreaseTraffic);
        iPickpockets = (Button) view.findViewById(R.id.buttonIncreasePickpockets);
        iHomeless = (Button) view.findViewById(R.id.buttonIncreaseHomeless);
        iKidnapping = (Button) view.findViewById(R.id.buttonIncreaseKidnapping);
        iPublicTransport = (Button) view.findViewById(R.id.buttonIncreasePublicTransport);
        iParties = (Button) view.findViewById(R.id.buttonIncreaseParties);
        iShops = (Button) view.findViewById(R.id.buttonIncreaseShops);
        iCarthefts = (Button) view.findViewById(R.id.buttonIncreaseCarThefts);

        dTraffic = (Button) view.findViewById(R.id.buttonDecreaseTraffic);
        dPickpockets = (Button) view.findViewById(R.id.buttonDecreasePickpockets);
        dHomeless = (Button) view.findViewById(R.id.buttonDecreaseHomeless);
        dKidnapping = (Button) view.findViewById(R.id.buttonDecreaseKidnapping);
        dPublicTransport = (Button) view.findViewById(R.id.buttonDecreasePublicTransport);
        dParties = (Button) view.findViewById(R.id.buttonDecreaseParties);
        dShops = (Button) view.findViewById(R.id.buttonDecreaseShops);
        dCarthefts = (Button) view.findViewById(R.id.buttonDecreaseCarThefts);

        textTraffic = (TextView) view.findViewById(R.id.textViewTraffic);
        textPickPocekets = (TextView) view.findViewById(R.id.textViewPickpocketsc);
        textHomeless = (TextView) view.findViewById(R.id.textViewHomeless);
        textKidnapping = (TextView) view.findViewById(R.id.textViewKidnapping);
        textPublicTransport = (TextView) view.findViewById(R.id.textViewPublicTransport);
        textParties = (TextView) view.findViewById(R.id.textViewParties);
        textShops = (TextView) view.findViewById(R.id.textViewShops);
        textCarthefts = (TextView) view.findViewById(R.id.textViewCarThefts);

        iTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true,textTraffic);
                traffic = Integer.parseInt(textTraffic.getText().toString());
            }
        });

        iPickpockets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textPickPocekets);
                pickpockets = Integer.parseInt(textPickPocekets.getText().toString());
            }
        });

        iHomeless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textHomeless);
                homeless = Integer.parseInt(textHomeless.getText().toString());
            }
        });

        iKidnapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textKidnapping);
                kidnapping = Integer.parseInt(textKidnapping.getText().toString());
            }
        });

        iPublicTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textPublicTransport);
                publicTransport = Integer.parseInt(textPublicTransport.getText().toString());
            }
        });

        iParties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textParties);
                parties = Integer.parseInt(textParties.getText().toString());
            }
        });

        iShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textShops);
                shops = Integer.parseInt(textShops.getText().toString());
            }
        });

        iCarthefts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textCarthefts);
                carthefts = Integer.parseInt(textCarthefts.getText().toString());
            }
        });

        dTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false,textTraffic);
                traffic = Integer.parseInt(textTraffic.getText().toString());
            }
        });

        dPickpockets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textPickPocekets);
                pickpockets = Integer.parseInt(textPickPocekets.getText().toString());
            }
        });

        dHomeless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textHomeless);
                homeless = Integer.parseInt(textHomeless.getText().toString());
            }
        });

        dKidnapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textKidnapping);
                kidnapping = Integer.parseInt(textKidnapping.getText().toString());
            }
        });

        dPublicTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textPublicTransport);
                publicTransport = Integer.parseInt(textPublicTransport.getText().toString());
            }
        });

        dParties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textParties);
                parties = Integer.parseInt(textParties.getText().toString());

            }
        });

        dShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textShops);
               shops = Integer.parseInt(textShops.getText().toString());
            }
        });

        dCarthefts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textCarthefts);
                carthefts = Integer.parseInt(textCarthefts.getText().toString());
            }
        });

        editTextComment = (EditText) view.findViewById(R.id.editComment);

            if( wasOpened == true){
                isSafe = getArguments().getBoolean("isSafe", true);
                traffic =  getArguments().getInt("traffic", 1);
                pickpockets =  getArguments().getInt("pickpockets", 1);
                kidnapping =  getArguments().getInt("kidnapping", 1);
                homeless =  getArguments().getInt("homeless", 1);
                publicTransport =  getArguments().getInt("publicTransport", 1);
                parties =  getArguments().getInt("parties", 1);
                shops =  getArguments().getInt("shops", 1);
                carthefts =  getArguments().getInt("carthefts", 1);
                circleRadius = getArguments().getDouble("circleRadius");
                comment = getArguments().getString("comment","");
                lat = getArguments().getDouble("latitude",1);
                longt = getArguments().getDouble("longitude",1);
                mCircleCenter = new LatLng(lat,longt);

                textTraffic.setText(String.valueOf(traffic));
                textPickPocekets.setText(String.valueOf(pickpockets));
                textHomeless.setText(String.valueOf(homeless));
                textKidnapping.setText(String.valueOf(kidnapping));
                textPublicTransport.setText(String.valueOf(publicTransport));
                textParties.setText(String.valueOf(parties));
                textShops.setText(String.valueOf(shops));
                textCarthefts.setText(String.valueOf(carthefts));
                editTextComment.setText(comment);
            }

        if(isSafe == false){
            radioButtonNotSafe.setChecked(true);
            radioButtonSafe.setChecked(false);
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    //SPRAWDZ JAK JEST PRZEKAZYWANE Z MAP DO FRAGMETNU W DOWORKU


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void changePosibility(boolean increase, TextView which){
        if(increase == true && Integer.parseInt(which.getText().toString()) < 5){
            if(which == textTraffic){
                textTraffic.setText(String.valueOf(Integer.parseInt(textTraffic.getText().toString())+1));
            }else if (which == textPickPocekets){
                textPickPocekets.setText(String.valueOf(Integer.parseInt(textPickPocekets.getText().toString())+1));
            }else if (which == textHomeless){
                textHomeless.setText(String.valueOf(Integer.parseInt(textHomeless.getText().toString())+1));
            }else if (which == textKidnapping){
                textKidnapping.setText(String.valueOf(Integer.parseInt(textKidnapping.getText().toString())+1));
            }else if (which == textPublicTransport){
                textPublicTransport.setText(String.valueOf(Integer.parseInt(textPublicTransport.getText().toString())+1));
            }else if (which == textParties){
                textParties.setText(String.valueOf(Integer.parseInt(textParties.getText().toString())+1));
            }else if (which == textShops){
                textShops.setText(String.valueOf(Integer.parseInt(textShops.getText().toString())+1));
            }else if (which == textCarthefts){
                textCarthefts.setText(String.valueOf(Integer.parseInt(textCarthefts.getText().toString())+1));
            }
        }else if(Integer.parseInt(which.getText().toString()) > 1 && increase == false) {
            if(which == textTraffic){
                textTraffic.setText(String.valueOf(Integer.parseInt(textTraffic.getText().toString())-1));
            }else if (which == textPickPocekets){
                textPickPocekets.setText(String.valueOf(Integer.parseInt(textPickPocekets.getText().toString())-1));
            }else if (which == textHomeless){
                textHomeless.setText(String.valueOf(Integer.parseInt(textHomeless.getText().toString())-1));
            }else if (which == textKidnapping){
                textKidnapping.setText(String.valueOf(Integer.parseInt(textKidnapping.getText().toString())-1));
            }else if (which == textPublicTransport){
                textPublicTransport.setText(String.valueOf(Integer.parseInt(textPublicTransport.getText().toString())-1));
            }else if (which == textParties){
                textParties.setText(String.valueOf(Integer.parseInt(textParties.getText().toString())-1));
            }else if (which == textShops){
                textShops.setText(String.valueOf(Integer.parseInt(textShops.getText().toString())-1));
            }else if (which == textCarthefts){
                textCarthefts.setText(String.valueOf(Integer.parseInt(textCarthefts.getText().toString())-1));
            }
        }
    }
}
