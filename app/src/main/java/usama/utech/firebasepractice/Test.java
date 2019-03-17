package usama.utech.firebasepractice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import usama.utech.firebasepractice.Adatpters.MyAdapter;
import usama.utech.firebasepractice.ModelClasses.User;

public class Test extends AppCompatActivity {

    private EditText name;
    private EditText age;
    private Button addData;
    private Button getData;
    private RecyclerView showDataTxt;
    ArrayList<User> data = new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");

        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        addData = (Button) findViewById(R.id.addData);
        getData = (Button) findViewById(R.id.getData);
        showDataTxt = (RecyclerView) findViewById(R.id.showDataTxt);


        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nam = name.getText().toString();
                String ag = age.getText().toString();


                DatabaseReference pushref = myRef.push();
                HashMap<String, String> map = new HashMap<>();
                map.put("id", pushref.getKey().toString());
                map.put("name", nam);
                map.put("age", ag);

                pushref.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Test.this, "data sdded", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Test.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        final MyAdapter adapter = new MyAdapter(data);
        showDataTxt.setLayoutManager(new LinearLayoutManager(this));
        showDataTxt.setAdapter(adapter);


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User value = dataSnapshot.getValue(User.class);
                data.add(value);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(Test.this, "Logouted", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Test.this, LoginPage.class));
                finish();
            }
        });


        findViewById(R.id.getSubData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference pushref = myRef.push();
                HashMap<String, String> map = new HashMap<>();
                map.put("id", pushref.getKey());
                map.put("name", "ali");
                map.put("age", "qwe");

                pushref.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Test.this, "data sdded", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Test.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }

        });

    }
}
