package usama.utech.firebasepractice;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import com.google.firebase.auth.AuthResult;

public class SignupPage extends AppCompatActivity {

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        toolbar = findViewById(R.id.toolbar_signup);
        toolbar.setTitle("Signup");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
//                        .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//
//                                    Toast.makeText(getApplicationContext(), "Success",Toast.LENGTH_SHORT).show();
//
//
//                                } else {
//
//                                    Toast.makeText(getApplicationContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
//
//                                }
//
//                                // ...
//                            }
//                        });
//
//
//            }
//        });
    }
}
