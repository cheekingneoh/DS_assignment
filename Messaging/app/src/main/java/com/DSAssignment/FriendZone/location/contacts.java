package com.DSAssignment.FriendZone.location;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.DSAssignment.FriendZone.DataStructures.LinkedList;
import com.DSAssignment.FriendZone.Messaging.MainActivity;
import com.DSAssignment.FriendZone.R;
import com.google.api.LogDescriptor;


public class contacts extends AppCompatActivity implements contactListAdapter.OnCardListener {
    private String temp;
    private RecyclerView list;
    String[] uid;
    String[] name;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Bundle extras = getIntent().getExtras();
        name = (String[]) extras.get("name");
        uid = (String[]) extras.get("uid");
        String[] gender = (String[]) extras.get("gender");
        int counter = (int) extras.get("count");

        Log.d("Size problem", Integer.toString(name.length));
        LinkedList found = new LinkedList();
        for (int i = 0; i < counter; i++) {
            found.add(new contactsFound(name[i], uid[i], gender[i]));
        }
        Log.d("Link list size", Integer.toString(found.size()));

        list = findViewById(R.id.contactList);
        contactListAdapter contactAdapter = new contactListAdapter(found, contacts.this, this);
        list.setAdapter(contactAdapter);
        list.setLayoutManager(new LinearLayoutManager(contacts.this));


        Button startBTN = findViewById(R.id.startButton);
        Button findUser = findViewById(R.id.find);
        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText UIDIN = findViewById(R.id.UIDinput);
                temp = UIDIN.getText().toString();
                Intent changeScreen = new Intent(contacts.this, MainActivity.class);
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



    @Override
    public void onCardClick(int position) {
        Intent changeScreen = new Intent(contacts.this, MainActivity.class);
        String tempUID=uid[position];
        String tempName=name[position];
        changeScreen.putExtra("OtherUID", tempUID);
        changeScreen.putExtra("name",tempName);
        Log.d("UID issue here", tempUID);
        startActivity(changeScreen);
    }
}