package com.example.admincollegeapp.clubs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.admincollegeapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ClubActivity extends AppCompatActivity {

    private RecyclerView clubListRV;
    private FloatingActionButton fabClub;

    private DatabaseReference reference,dbRef;

    private List<ClubData> list;
    private ClubAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);
        clubListRV = findViewById(R.id.clubListRV);


        reference = FirebaseDatabase.getInstance("https://my-college-app-32d40-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

        setRecyclerView();


        fabClub = findViewById(R.id.fabClub);


        fabClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddClub();
            }
        });

    }

    private void setRecyclerView() {
        Log.i("Check","inSetRecyclerView");
        dbRef = reference.child("Clubs");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                if (!snapshot.exists()) {
                    clubListRV.setVisibility(View.GONE);
                } else {
                    clubListRV.setVisibility(View.VISIBLE);

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ClubData data = dataSnapshot.getValue(ClubData.class);
                        list.add(data);
                    }
                    Log.i("ListSize",Integer.toString(list.size()));
                    clubListRV.setLayoutManager(new LinearLayoutManager(ClubActivity.this));
                    adapter = new ClubAdapter(list, ClubActivity.this);
                    Log.i("ListSize",Integer.toString(list.size()));
                    clubListRV.setAdapter(adapter);
                    Log.i("ListSize",Integer.toString(list.size()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ClubActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void openAddClub() {
        Intent intent = new Intent(ClubActivity.this, AddClubActivity.class);
        startActivity(intent);
    }
}