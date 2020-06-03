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

public class register extends AppCompatActivity {

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth=FirebaseAuth.getInstance();
        Button registerBTN=findViewById(R.id.registerButton);
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailIN=findViewById(R.id.emailIn);
                EditText passwordIN=findViewById(R.id.passwordIn);
                EditText passwordIN2=findViewById(R.id.passwordIn2);
                String email=emailIN.getText().toString();
                String password=passwordIN.getText().toString();
                String password2=passwordIN2.getText().toString();
                Boolean samePassword=password.equals(password2);

                if(email.isEmpty())
                    Toast.makeText(register.this, "Please enter an email", Toast.LENGTH_SHORT).show();
                else if(password.isEmpty())
                    Toast.makeText(register.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                else if(password2.isEmpty())
                    Toast.makeText(register.this, "Please re-enter your password to confirm it", Toast.LENGTH_SHORT).show();
                else if(!samePassword)
                    Toast.makeText(register.this, "Passwords entered do not match", Toast.LENGTH_SHORT).show();
                else{
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user=auth.getCurrentUser();
                                startActivity(new Intent(register.this, contacts.class));
                            }
                            else
                                Toast.makeText(register.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        final TextView signIn=findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, login.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=auth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(register.this, contacts.class));
        }
    }
}
