package com.example.collegeapp.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.collegeapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class EbookActivity extends AppCompatActivity {

    private RecyclerView ebookRecycler;
    private DatabaseReference reference;
    private List<EbookData> list;
    private EbookAdapter adapter;
    private ShimmerFrameLayout shimmerLoadingView;

    private LinearLayout shimmerLinearLayout;
    private EditText pdf_search;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);

        toolbar = findViewById(R.id.appbarEB);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("E-books");
        toolbar.setTitleTextAppearance(this, R.style.poppins_bold);

        ebookRecycler = findViewById(R.id.ebookRecycler);
        reference = FirebaseDatabase.getInstance().getReference().child("pdf");
        reference.keepSynced(true);
        shimmerLoadingView = findViewById(R.id.shimmerLoadingView);
        shimmerLinearLayout = findViewById(R.id.shimmerLinearLayout);
        pdf_search = findViewById(R.id.pdf_search);


        getData();
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    EbookData data = dataSnapshot.getValue(EbookData.class);
                    list.add(data);
                }

                adapter = new EbookAdapter(EbookActivity.this,list);
                ebookRecycler.setLayoutManager(new LinearLayoutManager(EbookActivity.this));
                ebookRecycler.setAdapter(adapter);
                shimmerLoadingView.stopShimmer();
                shimmerLinearLayout.setVisibility(View.GONE);
                pdf_search.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EbookActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        pdf_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text){
        ArrayList<EbookData> filterList = new ArrayList<>();
        for (EbookData item : list){
            if (item.getPdfTitle().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        adapter.FilteredList(filterList);

    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerLoadingView.startShimmer();
    }

    @Override
    protected void onPause() {
        shimmerLoadingView.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        shimmerLoadingView.startShimmer();
        super.onRestart();
    }

}