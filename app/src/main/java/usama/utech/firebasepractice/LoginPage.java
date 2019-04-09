package usama.utech.firebasepractice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {


    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText emailText;
    EditText passwordText;
    Button loginButton,signupButton;
    TextView  resetpass;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Write a message to the database
        database = FirebaseDatabase.getInstance();

        resetpass = findViewById(R.id.reset_pass);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.pass);
        loginButton = findViewById(R.id.loginBtn);
        signupButton = findViewById(R.id.signup);

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
                builder.setTitle("Reset Password");
                builder.setMessage("Enter Email");
                final EditText input = new EditText(LoginPage.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = input.getText().toString();

                        if (!email.equals("") && !email.equals(null)) {
                            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(LoginPage.this, "Check Your Inbox For Reset Email", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();


            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupPage.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginPage.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();

                            myRef = database.getReference("Users");

                            myRef.orderByChild("uid").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot datas : dataSnapshot.getChildren()) {

                                        String status = datas.child("status").getValue().toString();

                                        if (status.equals("false")) {
                                            progressDialog.dismiss();
                                            onAccountBlocked();
                                            return;

                                        }
                                        String username = datas.child("username").getValue().toString();

                                        String uid = datas.child("uid").getValue().toString();
                                        String email = datas.child("email").getValue().toString();
                                        String bio = datas.child("bio").getValue().toString();


                                        SharedPreferences.Editor editor = getSharedPreferences("saveddata", MODE_PRIVATE).edit();
                                        editor.putString("username", username);
                                        editor.putString("status", status);
                                        editor.putString("uid", uid);
                                        editor.putString("email", email);
                                        editor.putString("isLogedin", "true");


                                        editor.putString("fb", datas.child("linkFacebook").getValue().toString());
                                        editor.putString("tw", datas.child("linkTwitter").getValue().toString());
                                        editor.putString("ins", datas.child("linkInstagram").getValue().toString());

                                        editor.apply();

                                        new android.os.Handler().postDelayed(
                                                new Runnable() {
                                                    public void run() {
                                                        // On complete call either onLoginSuccess or onLoginFailed
                                                        onLoginSuccess();
                                                        // onLoginFailed();
                                                        progressDialog.dismiss();
                                                    }
                                                }, 1000);

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(LoginPage.this, "Canceled ",
                                            Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    loginButton.setEnabled(true);
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            loginButton.setEnabled(true);


                        }

                        // ...
                    }
                });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }



    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        startActivity(new Intent(this, Test.class));
        finish();
    }

    public void onAccountBlocked() {
        loginButton.setEnabled(true);


        AlertDialog.Builder diaBuilder = new AlertDialog.Builder(this);
        diaBuilder.setTitle("Account Blocked");
        diaBuilder.setCancelable(false);
        diaBuilder.setMessage("Your Account Has been blocked! for further information contact the admin at admin@admin.com");
        diaBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        diaBuilder.show();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address");
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

        return valid;
    }
}