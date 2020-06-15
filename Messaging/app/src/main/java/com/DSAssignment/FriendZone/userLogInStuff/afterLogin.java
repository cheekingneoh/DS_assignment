package com.DSAssignment.FriendZone.userLogInStuff;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.DSAssignment.FriendZone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class afterLogin extends AppCompatActivity implements View.OnClickListener {

    Button button;
    Button buttonInfo;
    FirebaseAuth auth;
    TextView currentUser;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        auth= FirebaseAuth.getInstance();
        button=findViewById(R.id.buttonLogout);
        buttonInfo=findViewById(R.id.buttonUpdateInfo);
        button.setOnClickListener(this);
        buttonInfo.setOnClickListener(this);
        currentUser=findViewById(R.id.textViewUser);
        dialog=new ProgressDialog(this);
        FirebaseUser user=auth.getCurrentUser();
        currentUser.setText(user.getEmail());
    }


    @Override
    public void onClick(View v) {
        if(v==button){
        dialog.setMessage("Logging out");
        dialog.show();
        auth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), login.class));
        dialog.dismiss();
        }

        if(v==buttonInfo){
            finish();
            startActivity(new Intent(getApplicationContext(), infoUpdate.class));

        }
    }
}

