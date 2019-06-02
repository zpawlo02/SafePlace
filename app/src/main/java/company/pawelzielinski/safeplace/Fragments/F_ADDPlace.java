package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import company.pawelzielinski.safeplace.MainMenu;
import company.pawelzielinski.safeplace.MapsActivity;
import company.pawelzielinski.safeplace.R;

public class F_ADDPlace extends Fragment {
    private View view;
    private Button buttonOpenMaps;
    private RadioButton radioButtonSafe, radioButtonNotSafe;
    private Boolean isSafe = true;

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
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_addplace, container, false);
        // Inflate the layout for this fragment

        buttonOpenMaps = (Button) view.findViewById(R.id.buttonOpenMaps);

        buttonOpenMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), MapsActivity.class);
                intent.putExtra("isSafe", isSafe);
                getActivity().startActivity(intent);
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
            }
        });

        iPickpockets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textPickPocekets);
            }
        });

        iHomeless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textHomeless);
            }
        });

        iKidnapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textKidnapping);
            }
        });

        iPublicTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textPublicTransport);
            }
        });

        iParties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textParties);
            }
        });

        iShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textShops);
            }
        });

        iCarthefts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(true, textCarthefts);
            }
        });

        dTraffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false,textTraffic);
            }
        });

        dPickpockets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textPickPocekets);
            }
        });

        dHomeless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textHomeless);
            }
        });

        dKidnapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textKidnapping);
            }
        });

        dPublicTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textPublicTransport);
            }
        });

        dParties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textParties);
            }
        });

        dShops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textShops);
            }
        });

        dCarthefts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePosibility(false, textCarthefts);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

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
