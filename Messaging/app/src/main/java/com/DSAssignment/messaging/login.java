    package com.DSAssignment.messaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

    public class login extends AppCompatActivity {

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        Button loginBtn = findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText emailIn = findViewById(R.id.emailInput);
                EditText passwordIn = findViewById(R.id.passwordInput);
                String email = emailIn.getText().toString();
                String password = passwordIn.getText().toString();


                if(email.isEmpty()&&password.isEmpty())
                    Toast.makeText(login.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                else if(email.isEmpty())
                    Toast.makeText(login.this,"Please enter your email",Toast.LENGTH_SHORT).show();
                else if(password.isEmpty())
                    Toast.makeText(login.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                else{
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user=auth.getCurrentUser();
                                startActivity(new Intent(login.this, contacts.class));
                            }
                            else{
                                Toast.makeText(login.this, "Login unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        final TextView registerBTN=findViewById(R.id.registrationButton);
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,register.class));
            }
        });
    }

    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser=auth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(login.this, contacts.class));
        }
    }
}
