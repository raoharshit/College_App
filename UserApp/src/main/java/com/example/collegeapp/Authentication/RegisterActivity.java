package com.example.collegeapp.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp.MainActivity;
import com.example.collegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText regName,regEmail,regPassword;
    private Button register;
    private String name,email,password;
    private ProgressDialog progressDialog;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private DatabaseReference dbRef;
    private StorageReference storageReference;

    private ImageView profilePic;

    private TextView openLog;

    private final int REQ = 1;
    private Bitmap bitmap;
    private static int PICK_IMAGE = 123;
    Uri imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        regName = findViewById(R.id.registerName);
        regEmail = findViewById(R.id.registerEmail);
        regPassword = findViewById(R.id.registerPass);
        register = findViewById(R.id.register);
        profilePic = findViewById(R.id.profilePic);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        openLog = findViewById(R.id.openLog);

        openLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void validateData() {
        name = regName.getText().toString();
        email = regEmail.getText().toString();
        password = regPassword.getText().toString();

        if(name.isEmpty()){
            regName.setError("Required");
            regName.requestFocus();
        }else if(email.isEmpty()){
            regEmail.setError("Required");
            regEmail.requestFocus();
        }else if(password.isEmpty()){
            regPassword.setError("Required");
            regPassword.requestFocus();
        }else if(imagePath == null){
            Toast.makeText(this, "Please choose a profile pic.", Toast.LENGTH_SHORT).show();
        }else{
            setProgressDialog();
            progressDialog.show();
            progressDialog.setCancelable(false);
            createUser();
        }
    }

    private void createUser() {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendEmailVerification();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void sendEmailVerification(){
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        uploadData();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Verification mail has not been sent.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void uploadData() {
        dbRef = reference.child("Users");
        UserProfile profile = new UserProfile(name,email);

        StorageReference imageReference = storageReference.child("Users").child(Objects.requireNonNull(auth.getUid())).child("Images").child("Profile Pic");
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    dbRef.child(auth.getUid()).setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Successfully Registered,Verification mail sent!", Toast.LENGTH_SHORT).show();
                                auth.signOut();
                                openLogin();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                profilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



}