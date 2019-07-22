package company.pawelzielinski.safeplace.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.util.Locale;

import company.pawelzielinski.safeplace.R;

public class ChangeLanguage extends Fragment {

    private Context context;
    private View view;
    private RadioButton radioButtonEnglish, radioButtonPolish, radioButtonRussish;

    public ChangeLanguage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_change_language, container, false);

        radioButtonEnglish = (RadioButton) view.findViewById(R.id.radioEnglish);
        radioButtonPolish = (RadioButton) view.findViewById(R.id.radioPolish);
        radioButtonRussish = (RadioButton) view.findViewById(R.id.radioRussian);

        radioButtonEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
                getActivity().recreate();
            }
        });

        radioButtonPolish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("pl");
                getActivity().recreate();
            }
        });

        radioButtonRussish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("ba");
                getActivity().recreate();

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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setLocale(String lamg){
        Locale locale = new Locale(lamg);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Settings",Context.MODE_PRIVATE ).edit();
        editor.putString("My_Lang", lamg);
        editor.apply();
    }

    private void loadLocale(){
        SharedPreferences prefs = getActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

}
