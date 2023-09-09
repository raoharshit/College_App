package com.example.collegeapp.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegeapp.MainActivity;
import com.example.collegeapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView openReg,openFor;
    private EditText logEmail,logPassword;
    private Button loginBtn;
    private ProgressDialog progressDialog;

    private String email, password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        openReg = findViewById(R.id.openReg);

        logEmail = findViewById(R.id.logEmail);
        logPassword = findViewById(R.id.logPass);
        loginBtn = findViewById(R.id.login);
        openFor = findViewById(R.id.openFor);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if(user != null){
            openMain();
        }

        openReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });
        
        openFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForget();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    private void openForget() {
        startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
    }

    private void validateData() {
        email = logEmail.getText().toString();
        password = logPassword.getText().toString();

        if(email.isEmpty()){
            logEmail.setError("Required");
            logEmail.requestFocus();
        }else if(password.isEmpty()){
            logPassword.setError("Required");
            logPassword.requestFocus();
        }else{
            setProgressDialog();
            progressDialog.show();
            progressDialog.setCancelable(false);
            loginUser();
        }
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    checkEmailVerification();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openMain() {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    private void openRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        Boolean emailFlag  = firebaseUser.isEmailVerified();

        if (emailFlag){
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
            openMain();
        }else{
            progressDialog.dismiss();
            Toast.makeText(this, "Verify your email.", Toast.LENGTH_SHORT).show();
            auth.signOut();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}