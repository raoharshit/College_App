package com.example.collegeapp.Developers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.collegeapp.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Developers extends AppCompatActivity {

    private Uri uri;

    private ImageView gmail;
    private CircleImageView linkedin;
    private CircleImageView github;
    private Intent intent;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        toolbar = findViewById(R.id.appbarDEV);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Developers");
        toolbar.setTitleTextAppearance(this, R.style.poppins_bold);

        gmail = findViewById(R.id.gmail);
        linkedin = findViewById(R.id.linkedin);
        github = findViewById(R.id.github);

        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse(getString(R.string.gmaillink));
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse(getString(R.string.linkedinlink));
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = Uri.parse(getString(R.string.githublink));
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}