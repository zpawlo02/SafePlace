package company.pawelzielinski.safeplace.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import company.pawelzielinski.safeplace.Classes.Place;
import company.pawelzielinski.safeplace.Classes.Username;
import company.pawelzielinski.safeplace.MainMenu;
import company.pawelzielinski.safeplace.R;
import company.pawelzielinski.safeplace.RegisterActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EditUsername extends Fragment {

    private EditText username;
    private Button save;
    private View view;
    private Context context;


    public EditUsername() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_username, container, false);

        username = (EditText) view.findViewById(R.id.userUsername);
        save = (Button) view.findViewById(R.id.buttonSaveUsername);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userName = username.getText().toString();


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference ref = db.collection("usernames").document();

                db.collection("usernames").whereEqualTo("username",userName).addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        int counterToCheck = 0;

                        if((queryDocumentSnapshots != null ? queryDocumentSnapshots.getDocumentChanges() : null) != null){
                            for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                                if (documentChange.getDocument().get("username").toString().equals(userName)) {
                                    counterToCheck++;
                                }
                            }
                        }

                            if (counterToCheck == 0){

                                ref.set(new Username(userName));
                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(userName)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    getFragmentManager().popBackStackImmediate();
                                                }
                                            }
                                        });

                            }else if(counterToCheck > 0){
                                Toast.makeText(getApplicationContext(),"This username exists", Toast.LENGTH_LONG).show();
                            }
                        }

                });



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
