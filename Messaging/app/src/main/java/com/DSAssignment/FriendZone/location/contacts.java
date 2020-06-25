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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
        Log.d("maybe problem here",Integer.toString(counter));
        setUserLocation();

        Log.d("Size problem", Integer.toString(name.length));
        LinkedList found = new LinkedList();
        for (int i = 0; i < counter; i++) {
            Log.d("maybe here",Integer.toString(i));
            found.add(new contactsFound(name[i], uid[i], gender[i]));
            Log.d("anpother test",found.toString());
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

    private LocationListener locationListener;
    private LocationManager locationManager;
    private DatabaseReference databaseLocation;
    String UserName;
    String UserGender;
    String UserId;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setUserLocation(){
        databaseLocation= FirebaseDatabase.getInstance().getReference().child("UserLocation");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                FirebaseUser FireUser=auth.getCurrentUser();
                String id=FireUser.getUid().toString();
                UserLocation userLocation=new UserLocation(location.getLatitude(),location.getLongitude(),getName(),getId(),getGender());
                databaseLocation.child(id).setValue(userLocation);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET
            },10);
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }

    public String getName(){

        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        String uid=user.getUid();
        DatabaseReference userDatabase= FirebaseDatabase.getInstance().getReference().child("User");
        userDatabase.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserName=dataSnapshot.child("name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return UserName;
    }

    public String getGender(){

        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        String uid=user.getUid();
        DatabaseReference userDatabase=FirebaseDatabase.getInstance().getReference().child("User");
        userDatabase.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserGender=dataSnapshot.child("gender").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return UserGender;
    }

    public String getId(){

        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        String uid=user.getUid();
        DatabaseReference userDatabase=FirebaseDatabase.getInstance().getReference().child("User");
        userDatabase.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserId=dataSnapshot.child("id").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return UserId;
    }
}
