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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admincollegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class UpdateClubActivity extends AppCompatActivity {

    private ImageView updateClubImage;
    private EditText updateClubName,updateFacultyCoord,updateStudentCoord,updateContact;
    private Button updateClubBtn,deleteClubBtn;


    private String name,facCor,stuCor,contact,image;

    private final int REQ = 1;
    private Bitmap bitmap = null;
    private ProgressDialog pd;
    private String downloadUrl,uniqueKey;

    private DatabaseReference reference,dbRef;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_club);

        name = getIntent().getStringExtra("name");
        facCor = getIntent().getStringExtra("facCor");
        stuCor = getIntent().getStringExtra("stuCor");
        image = getIntent().getStringExtra("image");
        contact = getIntent().getStringExtra("contact");
        uniqueKey = getIntent().getStringExtra("key");

        updateClubImage = findViewById(R.id.updateClubImage);
        updateClubName = findViewById(R.id.updateClubName);
        updateFacultyCoord = findViewById(R.id.updateFacultyCoord);
        updateStudentCoord = findViewById(R.id.updateStudentCoord);
        updateContact = findViewById(R.id.updateContact);
        updateClubBtn = findViewById(R.id.updateClubBtn);
        deleteClubBtn = findViewById(R.id.deleteClubBtn);

        pd = new ProgressDialog(this);


        reference = FirebaseDatabase.getInstance("https://my-college-app-32d40-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        storageReference = FirebaseStorage.getInstance().getReference();


        try {
            Picasso.get().load(image).into(updateClubImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateClubName.setText(name);
        updateFacultyCoord.setText(facCor);
        updateStudentCoord.setText(stuCor);
        updateContact.setText(contact);

        updateClubImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        updateClubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = updateClubName.getText().toString();
                facCor = updateFacultyCoord.getText().toString();
                stuCor = updateStudentCoord.getText().toString();
                contact = updateContact.getText().toString();
                checkValidation();
            }
        });

        deleteClubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void checkValidation() {
        if(name.isEmpty()){
            updateClubName.setError("Empty");
            updateClubName.requestFocus();
        }else if(facCor.isEmpty()){
            updateFacultyCoord.setError("Empty");
            updateFacultyCoord.requestFocus();
        }else if(stuCor.isEmpty()){
            updateStudentCoord.setError("Empty");
            updateStudentCoord.requestFocus();
        }else if(contact.isEmpty()){
            updateContact.setError("Empty");
            updateContact.requestFocus();
        }else if(bitmap == null){
            updateData(image);
        }else{
            uploadImage();
        }
    }

    private void updateData(String s) {
        pd.setMessage("Updating...");
        pd.show();

        HashMap hp = new HashMap();
        hp.put("name",name);
        hp.put("facCor",facCor);
        hp.put("stuCor",stuCor);
        hp.put("image",s);
        hp.put("contact",contact);

        dbRef = reference.child("Clubs");

        dbRef.child(uniqueKey).updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                pd.dismiss();
                Toast.makeText(UpdateClubActivity.this, "Club Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateClubActivity.this, ClubActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateClubActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadImage() {
        pd.setMessage("Updating...");
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImg = baos.toByteArray();
        final StorageReference filePath;
        filePath = storageReference.child("Clubs").child(finalImg +"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(UpdateClubActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                }else{
                    pd.dismiss();
                    Toast.makeText(UpdateClubActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void deleteData() {
        pd.setMessage("Deleting...");
        pd.show();
        dbRef = reference.child("Clubs").child(uniqueKey);
        dbRef.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        pd.dismiss();
                        Toast.makeText(UpdateClubActivity.this, "Teacher Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateClubActivity.this,ClubActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(UpdateClubActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
            updateClubImage.setImageBitmap(bitmap);
        }
    }
}