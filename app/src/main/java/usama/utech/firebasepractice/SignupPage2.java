package usama.utech.firebasepractice;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupPage2 extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page2);


        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Write a message to the database
        database = FirebaseDatabase.getInstance();


    }



    public void signup() {



        final ProgressDialog progressDialog = new ProgressDialog(SignupPage2.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();



        final String email = emailText.getText().toString();

        final String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.
        if ( !email.equals("") && !password.equals("") && !reEnterPassword.equals("")) {

            if (password.equals(reEnterPassword)) {



                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    myRef = database.getReference("Users");


                                    DatabaseReference pushref = myRef.push();
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("id", pushref.getKey());
                                    map.put("uid", user.getUid());

                                    map.put("email", email);
                                    map.put("status", "true");
                                    map.put("reported", "false");
                                    map.put("linkFacebook", "");
                                    map.put("linkTwitter", "");
                                    map.put("linkInstagram", "");
                                    map.put("bio","");

                                    pushref.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                new android.os.Handler().postDelayed(
                                                        new Runnable() {
                                                            public void run() {
                                                                // On complete call either onSignupSuccess or onSignupFailed
                                                                // depending on success

                                                                // onSignupFailed();
                                                                progressDialog.dismiss();


                                                            }
                                                        }, 1000);

                                            } else {
                                                Toast.makeText(SignupPage2.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });


                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignupPage2.this, "User Creation failed.",
                                            Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();



                                }

                                // ...
                            }
                        });


            } else {
                Toast.makeText(this, "Password Not Matching!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter Empty Feilds!", Toast.LENGTH_LONG).show();
        }

    }



}
