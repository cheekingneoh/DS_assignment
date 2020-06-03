package com.DSAssignment.messaging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class contacts extends AppCompatActivity {
    private String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Button startBTN=findViewById(R.id.startButton);
        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText UIDIN=findViewById(R.id.UIDinput);
                temp=UIDIN.getText().toString();
                Intent changeScreen=new Intent(contacts.this,MainActivity.class);
                changeScreen.putExtra("OtherUID", temp);
                startActivity(changeScreen);
            }
        });
    }

    public String getOtherUID(){
        return temp;
    }
}
