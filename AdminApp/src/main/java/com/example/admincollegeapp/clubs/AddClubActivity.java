package com.example.admincollegeapp.clubs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admincollegeapp.R;
import com.example.admincollegeapp.faculty.AddTeachers;
import com.example.admincollegeapp.faculty.TeacherData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddClubActivity extends AppCompatActivity {

    private EditText addClubName,addFacultyCoordinator,addStudentCoordinator,addContact;
    private CircleImageView addClubImage;
    private Button addClubBtn;

    private DatabaseReference reference,dbRef;
    private StorageReference storageReference;

    private String name,facCor,stuCor,contact,downloadUrl="";

    private Bitmap bitmap;
    private ProgressDialog pd;
    private final int REQ = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_club);

        addClubName = findViewById(R.id.addClubName);
        addFacultyCoordinator = findViewById(R.id.addFacultyCoordinator);
        addStudentCoordinator = findViewById(R.id.addStudentCoordinator);
        addContact = findViewById(R.id.addContact);
        addClubImage = findViewById(R.id.addClubImage);
        addClubBtn = findViewById(R.id.addClubBtn);
        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance("https://my-college-app-32d40-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        addClubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        addClubImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addClubImage.setImageBitmap(bitmap);
        }
    }

    private void validateData() {
        name = addClubName.getText().toString();
        facCor = addFacultyCoordinator.getText().toString();
        stuCor = addStudentCoordinator.getText().toString();
        contact = addContact.getText().toString();
        if(name.isEmpty()){
            addClubName.setError("Empty");
            addClubName.requestFocus();
        }else if(facCor.isEmpty()){
            addFacultyCoordinator.setError("Empty");
            addFacultyCoordinator.requestFocus();
        }else if(stuCor.isEmpty()){
            addStudentCoordinator.setError("Empty");
            addStudentCoordinator.requestFocus();
        }else if(contact.isEmpty()){
            addContact.setError("Empty");
            addContact.requestFocus();
        }else if(bitmap == null){
            uploadData();
        }else{
            uploadImage();
        }
    }

    private void uploadImage() {
        pd.setMessage("Uploading...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Clubs").child(finalImg +"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(AddClubActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(AddClubActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        pd.setMessage("Uploading...");
        pd.show();
        dbRef = reference.child("Clubs");
        final String uniqueKey = dbRef.push().getKey();

        ClubData clubData = new ClubData(name,facCor,stuCor,contact,downloadUrl,uniqueKey);

        dbRef.child(uniqueKey).setValue(clubData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(AddClubActivity.this, "Club Added", Toast.LENGTH_SHORT).show();
                openClubActivity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddClubActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openClubActivity() {
        Intent intent = new Intent(AddClubActivity.this,ClubActivity.class);
        startActivity(intent);
        finish();
    }
}