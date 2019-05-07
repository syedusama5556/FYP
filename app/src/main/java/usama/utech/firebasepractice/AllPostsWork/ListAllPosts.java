package usama.utech.firebasepractice.AllPostsWork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import usama.utech.firebasepractice.Adatpters.PostAdapter;
import usama.utech.firebasepractice.ModelClasses.Posts;
import usama.utech.firebasepractice.ModelClasses.User;
import usama.utech.firebasepractice.R;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListAllPosts extends AppCompatActivity {

    RecyclerView recyclerView;
    PostAdapter postAdapter;
    ArrayList<Posts> postsArrayList = new ArrayList<>();


    DatabaseReference myRef;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_posts);

        recyclerView = findViewById(R.id.rec_posts_list_all_posts);

        myRef = FirebaseDatabase.getInstance().getReference("PostsAsDriver");

      postAdapter = new PostAdapter(this ,postsArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Posts value = dataSnapshot.getValue(Posts.class);
                postsArrayList.add(value);
                postAdapter.notifyDataSetChanged();
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



    }
}
