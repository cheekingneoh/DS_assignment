package com.DSAssignment.FriendZone.location;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class getLocation extends AppCompatActivity {
    private LocationListener locationListener;
    private LocationManager locationManager;
    private DatabaseReference databaseLocation;
    String UserName;
    public getLocation(){

    }

    public String getName(){

        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        String uid=user.getUid();
        DatabaseReference userDatabase=FirebaseDatabase.getInstance().getReference().child("Users");
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public  void setUserLocation(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                FirebaseAuth auth=FirebaseAuth.getInstance();
                FirebaseUser FireUser=auth.getCurrentUser();
                databaseLocation=FirebaseDatabase.getInstance().getReference().child("UserLocation");
                String id=FireUser.getUid().toString();

                UserLocation userLocation=new UserLocation(location.getLatitude(),location.getLongitude(),getName());
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
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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
}
