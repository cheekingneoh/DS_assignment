package com.DSAssignment.FriendZone.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.DSAssignment.FriendZone.Messaging.MainActivity;
import com.DSAssignment.FriendZone.R;

public class contacts extends AppCompatActivity {
    private String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Button startBTN=findViewById(R.id.startButton);
        Button findUser=findViewById(R.id.find);
        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText UIDIN=findViewById(R.id.UIDinput);
                temp=UIDIN.getText().toString();
                Intent changeScreen=new Intent(contacts.this, MainActivity.class);
                changeScreen.putExtra("OtherUID", temp);
                startActivity(changeScreen);
            }
        });
        findUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(contacts.this, FindUser.class));
            }
        });
    }

    public String getOtherUID(){
        return temp;
    }
}
