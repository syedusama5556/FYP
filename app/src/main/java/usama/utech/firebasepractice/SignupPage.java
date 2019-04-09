package usama.utech.firebasepractice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupPage extends AppCompatActivity {


    private static final String TAG = "SignupActivity";


    EditText firstName;
    EditText lastName;
    EditText cnicSignup;
    EditText phonenoSignup;
    EditText signupProvence;
    EditText signupCity;



    EditText emailText;
    EditText passwordText;
    EditText reEnterPasswordText;

    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Write a message to the database
        database = FirebaseDatabase.getInstance();



        firstName= findViewById(R.id.first_name);
        lastName= findViewById(R.id.last_name);
        cnicSignup= findViewById(R.id.cnic_signup);
        phonenoSignup= findViewById(R.id.phoneno_signup);
        signupProvence= findViewById(R.id.signup_provence);
        signupCity= findViewById(R.id.signup_city);


        emailText = findViewById(R.id.signup_email);
        passwordText = findViewById(R.id.pass_signup);
        reEnterPasswordText = findViewById(R.id.confirm_pass_signup);




        findViewById(R.id.contniue_as_driver_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Signup");

                if (!validate()) {
                return;
            }



            final String email = emailText.getText().toString();

            final String password = passwordText.getText().toString();
            String reEnterPassword = reEnterPasswordText.getText().toString();

                if (!email.equals("") && !password.equals("") && !reEnterPassword.equals("") && !firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") && !cnicSignup.getText().toString().equals("") && !signupProvence.getText().toString().equals("") && !signupCity.getText().toString().equals("")) {


                    if (password.equals(reEnterPassword)) {


                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

                    ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {

                                gotoSignupPage2WithIntentData();
                            } else {
                                Toast.makeText(SignupPage.this, "Email exists", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }
            }
            //gotoSignupPage2WithIntentData();
        }
    });


    findViewById(R.id.contniue_as_passenger_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  Log.d(TAG, "Signup");

                if (!validate()) {
                    return;
                }



                final String email = emailText.getText().toString();

                final String password = passwordText.getText().toString();
                String reEnterPassword = reEnterPasswordText.getText().toString();

                // TODO: Implement your own gotoSignupPage2WithIntentData logic here.
                if (!email.equals("") && !password.equals("") && !reEnterPassword.equals("") && !firstName.getText().toString().equals("") && !lastName.getText().toString().equals("") && !cnicSignup.getText().toString().equals("") && !signupProvence.getText().toString().equals("") && !signupCity.getText().toString().equals("")) {

                    if (password.equals(reEnterPassword)) {


                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

                        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {

                                    gotoSignupPage2WithIntentData();
                                } else {
                                    Toast.makeText(SignupPage.this, "Email exists", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    }
                }
                //gotoSignupPage2WithIntentData();
            }
        });







    }

    private void gotoSignupPage2WithIntentData() {

        Intent intent = new Intent(getApplicationContext(),SignupPage2.class);

        intent.putExtra("fname",firstName.getText().toString());
        intent.putExtra("lname",lastName.getText().toString());
        intent.putExtra("cnic",cnicSignup.getText().toString());
        intent.putExtra("phoneno",phonenoSignup.getText().toString());
        intent.putExtra("provence",signupProvence.getText().toString());
        intent.putExtra("city",signupCity.getText().toString());


        intent.putExtra("email",emailText.getText().toString());
        intent.putExtra("pass",passwordText.getText().toString());

        startActivity(intent);




    }


    public boolean validate() {
        boolean valid = true;



        String email = emailText.getText().toString();

        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();




        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }


}