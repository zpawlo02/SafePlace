package company.pawelzielinski.safeplace.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import company.pawelzielinski.safeplace.R;

public class ResetPassword extends Fragment {

    private Context context;
    private EditText editTextEmail;
    private Button buttonReset;
    private View view;
    private FirebaseAuth auth;

    public ResetPassword() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        editTextEmail = (EditText) view.findViewById(R.id.editTextEmailReset);
        buttonReset = (Button) view.findViewById(R.id.buttonReset);

        auth = FirebaseAuth.getInstance();

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(context, "Put your email!", Toast.LENGTH_SHORT);
                }else {
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Email has been sent!", Toast.LENGTH_SHORT);
                            getActivity().onBackPressed();
                        }
                    });
                }
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

}
