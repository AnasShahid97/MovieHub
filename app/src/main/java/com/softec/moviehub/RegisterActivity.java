package com.softec.moviehub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    TextInputLayout userName, email, password, confirmPassword;
    Button registerBtn, signInBtn, chooseGenreBtn;
    FirebaseAuth auth;
    private String userEmail, userPassword;
    Dialog dialog;

    AlertDialog.Builder builder;


    final String[] items = {"Action", "Adventure", "Sci-Fi", "Drama", "Documentary"};

    final ArrayList itemsSelected = new ArrayList();

    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);

        userName = findViewById(R.id.Register_userName);
        email = findViewById(R.id.Register_email);
        password = findViewById(R.id.Register_password);
        confirmPassword = findViewById(R.id.Register_confirmPassword);
        registerBtn = findViewById(R.id.Register_registerBtn);
        signInBtn = findViewById(R.id.Register_signInBtn);
        chooseGenreBtn = findViewById(R.id.Register_genreBtn);
        auth=FirebaseAuth.getInstance();


        chooseGenreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {

            String name = userName.getEditText().getText().toString();
            String userConfirmPassword = confirmPassword.getEditText().getText().toString();

            @Override
            public void onClick(View v) {

                userEmail = email.getEditText().getText().toString();
                userPassword = password.getEditText().getText().toString();
                auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                            intent.putExtra("userName", name);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Failed to create account"+task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private boolean validateName(String name){
        if(name.equals("")){
            Toast.makeText(RegisterActivity.this,"Name can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean validateEmail(String email){
        if(email.equals("")){
            Toast.makeText(RegisterActivity.this,"Email can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean validatePassword(String password){
        if(password.equals("")){
            Toast.makeText(RegisterActivity.this,"Password can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean validateConfirmPassword(String password){
        if(password.equals("")){
            Toast.makeText(RegisterActivity.this,"Confirmation Password can't be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void show(){
        builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Choose genres of your choice:");

        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    itemsSelected.add(which);
                }
                else if(itemsSelected.contains(which)){
                    itemsSelected.remove(Integer.valueOf(which));
                }
            }
        }).setPositiveButton("Done!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.show();


    }



}
