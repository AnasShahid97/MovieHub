package com.softec.moviehub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    TextInputLayout email, password;
    Button signInBtn, createAccountBtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        FirebaseApp.initializeApp(this);

        email = findViewById(R.id.SignIn_emailInput);
        password = findViewById(R.id.SignIn_password);
        signInBtn = findViewById(R.id.SignIn_signInBtn);
        createAccountBtn = findViewById(R.id.SignIn_createAccountBtn);
        auth=FirebaseAuth.getInstance();

        final String userName = getIntent().getStringExtra("userName");

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getEditText().getText().toString();
                String passwordInput = password.getEditText().getText().toString();

                if(emailInput.equals("")){
                    Toast.makeText(SignInActivity.this, "Email can't be empty", Toast.LENGTH_LONG).show();
                }
                else if(passwordInput.equals("")){
                    Toast.makeText(SignInActivity.this, "Password can't be empty", Toast.LENGTH_LONG).show();
                }
                else{
                    auth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignInActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                intent.putExtra("userName", userName);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(SignInActivity.this, "Failed to sign in"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
