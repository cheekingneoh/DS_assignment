package com.DSAssignment.FriendZone.userLogInStuff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.DSAssignment.FriendZone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText emailText;
    EditText passwordText;
    EditText passwordConfirmation;
    TextView logninText;
    TextView logninOtherAcc;
    Button button;
    Toast toast;
    ProgressDialog dialog;
    FirebaseAuth auth;
    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference ref=db.getReference();
    protected static LinkedList<String> list=new LinkedList<>();
    protected String email;
    String password;
    String password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth= FirebaseAuth.getInstance();
        emailText=findViewById(R.id.editTextForEmail2);
        passwordText=findViewById(R.id.editTextForPassword2);
        passwordConfirmation=findViewById(R.id.editTextForPasswordConfirmation2) ;
        logninText=findViewById(R.id.textViewLogin2);
        button=findViewById(R.id.buttonForSignIn2);
        dialog=new ProgressDialog(this);
        button.setOnClickListener(this);
        logninText.setOnClickListener(this);
        logninOtherAcc=findViewById(R.id.textViewSignInOtherAcc);
        logninOtherAcc.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        if(v==button){
            registerUser();



        }
        if(v==logninText){
            //open login activity
            finish();
            startActivity(new Intent(this, login.class));
        }

        if(v==logninOtherAcc){
            finish();
            startActivity(new Intent(this, LoginUsingOtherAcc.class));
        }

    }

    public void registerUser(){
        email=emailText.getText().toString();
         password=passwordText.getText().toString();
        password2=passwordConfirmation.getText().toString();

        if(TextUtils.isEmpty(email)){
            emailText.setError("Cannot be empty");
            emailText.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            passwordText.setError("Password cannot be empty");
            passwordText.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password2)){
            passwordConfirmation.setError("Please confirm your password");
            passwordConfirmation.requestFocus();
            return;
        }

        if(password.length()<=6||password2.length()<=6){
            passwordText.setError("Password length must be more than 6 characters");
            passwordText.requestFocus();
            return;
        }

        if(!password.equals(password2)){
            passwordConfirmation.setError("Password confirmation does not match");
            passwordConfirmation.requestFocus();
            return;
        }
        dialog.setMessage("Registering User");
        dialog.show();

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                dialog.dismiss();
                if (task.isSuccessful()) {
                    toast.makeText(SignUp.this, "Registered", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), infoUpdate.class));


                } else {
                    toast.makeText(SignUp.this, "Register failed. Please try again", Toast.LENGTH_SHORT).show();

                }


            }
        });




    }
}