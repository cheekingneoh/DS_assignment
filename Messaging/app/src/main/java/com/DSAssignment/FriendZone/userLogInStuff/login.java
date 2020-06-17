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

import com.DSAssignment.FriendZone.location.FindUser;
import com.DSAssignment.FriendZone.location.contacts;
import com.DSAssignment.FriendZone.Messaging.storeWord;
import com.DSAssignment.FriendZone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;


public class login extends AppCompatActivity implements View.OnClickListener {

    EditText emailText;
    EditText passwordText;
    Button button;
    TextView signupText;
    TextView signinAcc;
    static LinkedList<String> list=new LinkedList<>();

    ProgressDialog dialog;
    Toast toast;
    FirebaseAuth auth= FirebaseAuth.getInstance();
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference ref=database.getReference();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        storeWord.createHash();


        //user didnt log out (optional)
//        if(auth.getCurrentUser()!=null){
//            Toast.makeText(this, "You're in!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, infoUpdate.class));
//        }

        emailText=findViewById(R.id.editTextForEmail);
        passwordText=findViewById(R.id.editTextForPassword);
        button=findViewById(R.id.buttonForLogIn);
        signupText=findViewById(R.id.textViewSignUp);
        signinAcc=findViewById(R.id.textViewSignInOtherAcc);
        dialog=new ProgressDialog(this);
        button.setOnClickListener(this);
        signupText.setOnClickListener(this);
        signinAcc.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v==button){
            Login();
        }
        if(v==signupText){
            finish();
            startActivity(new Intent(login.this, SignUp.class));
        }

        if(v==signinAcc){
            finish();
            startActivity(new Intent(login.this, LoginUsingOtherAcc.class));
        }
    }


    public void Login() {

        userLogin loggingin=new userLogin();
        loggingin.setEmail (emailText.getText().toString());
        loggingin.setPassword(passwordText.getText().toString());

        if (TextUtils.isEmpty(emailText.getText().toString())) {
            emailText.setError("Cannot be empty");
            emailText.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(passwordText.getText().toString())) {
            passwordText.setError("Password cannot be empty");
            passwordText.requestFocus();
            return;
        }

        dialog.setMessage("Logging in");
        dialog.show();


            auth.signInWithEmailAndPassword(loggingin.getEmail(), loggingin.getPassword()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    dialog.dismiss();
                    if (task.isSuccessful()) {


                        toast.makeText(login.this, "You're in!", Toast.LENGTH_SHORT).show();

                        FirebaseUser currentUser = auth.getCurrentUser();
                        final String uniqueID = currentUser.getUid();
                        ref.child("User").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.child(uniqueID).exists()) {
                                    finish();
                                    startActivity(new Intent(login.this, FindUser.class));

                                } else {
                                    finish();
                                    startActivity(new Intent(login.this, infoUpdate.class));
                                }


                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                toast.makeText(login.this, "Error in database", Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else
                        Toast.makeText(login.this, "Unsuccessful. Please make sure your email and password are correct", Toast.LENGTH_SHORT).show();

                }
            });
        }



    }







