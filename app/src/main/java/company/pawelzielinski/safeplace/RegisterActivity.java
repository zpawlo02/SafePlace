package company.pawelzielinski.safeplace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.common.base.CharMatcher;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import company.pawelzielinski.safeplace.Classes.Username;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private EditText email, password, username;;
    private Button buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        username = (EditText) findViewById(R.id.login);

        buttonRegister = (Button) findViewById(R.id.register_button);


        mAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().matches("")
                        || password.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "You must fill all fields!", Toast.LENGTH_SHORT).show();

                }else {
                    register();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

    }

    private void register(){
        int counter = 0;
        final String usernameS = username.getText().toString();

        for(int i = 0; i < usernameS.length(); i++){
            if(usernameS.charAt(i) == ' '){
                counter++;
            }
        }

        if(counter > 0){
            Toast.makeText(getApplicationContext(), "Username can not contain space characters", Toast.LENGTH_SHORT).show();
        }else {

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            final DocumentReference ref = db.collection("usernames").document();
            db.enableNetwork();
            db.collection("usernames").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    Integer counterToCheck = 0;

                    if(counterToCheck == 0){
                        authorrisation(ref);
                        counterToCheck = 0;
                    }else if (counterToCheck > 0){
                        Toast.makeText(getApplicationContext(), "This username exists", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }

    public void  authorrisation(final DocumentReference ref){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username.getText().toString())
                                    .build();

                            ref.set(new Username(username.getText().toString())).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), user.getDisplayName() + " connected!", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(RegisterActivity.this, MainMenu.class));
                                                Log.d("updated", "User profile updated.");
                                            }
                                        }
                                    });
                            startActivity(new Intent(RegisterActivity.this, MainMenu.class));
                            Log.d("complete", "createUserWithEmail:success");
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

}