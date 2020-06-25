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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.DSAssignment.FriendZone.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;

public class FindUser extends AppCompatActivity {

    private DatabaseReference databaseUser;
    private FirebaseAuth auth;
    private String currentUserId;
    private double currentLatitude;
    private double currentLongitude;
    private String currentName;
    private String currentId;
    private String currentId2;
    final private double MAX=500.0;
    LinkedList<Double> latitudeList=new LinkedList<>();
    LinkedList<Double> longitudeList=new LinkedList<>();
    LinkedList<String> nameList=new LinkedList<>();
    LinkedList<Double> distance=new LinkedList<>();

    private DatabaseReference databaseUser2;
    private String currentSport;
    private String currentMovie;
    private String currentMusic;
    private String currentBook;
    private String currentFood;
    private String currentTravel;
    private String currentgenderInterested;
    LinkedList<String> sportList=new LinkedList<>();
    LinkedList<String> movieList=new LinkedList<>();
    LinkedList<String> musicList=new LinkedList<>();
    LinkedList<String> bookList=new LinkedList<>();
    LinkedList<String> foodList=new LinkedList<>();
    LinkedList<String> travelList=new LinkedList<>();
    LinkedList<String> genderList=new LinkedList<>();
    LinkedList<String> genderNearby=new LinkedList<>();
    LinkedList<String> nameNearby=new LinkedList<>();
    LinkedList<Integer> weightage=new LinkedList<>();
    LinkedList<String> idList=new LinkedList<>();
    LinkedList<String> idNearby=new LinkedList<>();
    LinkedList<Boolean> filter=new LinkedList<>();
    ArrayList <contactsFound> found=new ArrayList<>();
    String[] name;
    String[] uid;
    String[] gender;
    int count;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_user);
        Button button=findViewById(R.id.button2);
        final TextView textView=findViewById(R.id.textView3);
        final TextView textView3=findViewById(R.id.textView5);
        final TextView textView1= findViewById(R.id.weightage_Display);
        databaseUser = FirebaseDatabase.getInstance().getReference().child("UserLocation");
        databaseUser2 = FirebaseDatabase.getInstance().getReference().child("User");
        auth=FirebaseAuth.getInstance();
        currentUserId=auth.getCurrentUser().getUid();
        setUserLocation();

        databaseUser.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentLatitude=dataSnapshot.child("latitude").getValue(Double.class);
                currentLongitude=dataSnapshot.child("longitude").getValue(Double.class);
                currentName=dataSnapshot.child("name").getValue(String.class);
                currentId=dataSnapshot.child("id").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                collectLatitude((Map<Double,Object>) dataSnapshot.getValue());
                collectLongitude((Map<Double,Object>) dataSnapshot.getValue());
                collectName((Map<String,Object>) dataSnapshot.getValue());
                collectId((Map<String,Object>) dataSnapshot.getValue());
                collectGender((Map<String,Object>) dataSnapshot.getValue());
                calcDistance(latitudeList,longitudeList);
                double currentSearch=100.0;
                count=0;
                while(currentSearch!=MAX){
                    for(int i=0;i<distance.size();i++){
                        if((Double)distance.get(i)<currentSearch){
                            nameNearby.add(nameList.get(i));
                            idNearby.add(idList.get(i));
                            genderNearby.add(genderList.get(i));
                            count++;
                        }
                    }
                    if(count!=0){
                        break;
                    }
                    currentSearch+=100.0;
                }
                if(count==0){
                    textView.append("\nThere is no user around "+MAX+" metres");
                }
                name=new String[count];
                uid=new String[count];
                gender=new String[count];
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseUser2.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        currentSport=dataSnapshot.child("sport").getValue(String.class);
                        currentMovie=dataSnapshot.child("movie").getValue(String.class);
                        currentMusic=dataSnapshot.child("music").getValue(String.class);
                        currentBook=dataSnapshot.child("book").getValue(String.class);
                        currentFood=dataSnapshot.child("food").getValue(String.class);
                        currentTravel=dataSnapshot.child("travel").getValue(String.class);
                        currentgenderInterested=dataSnapshot.child("genderInterested").getValue(String.class);
                        currentId2=dataSnapshot.child("id").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                databaseUser2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        collectSport((Map<String,Object>) dataSnapshot.getValue());
                        collectMovie((Map<String,Object>) dataSnapshot.getValue());
                        collectMusic((Map<String,Object>) dataSnapshot.getValue());
                        collectBook((Map<String,Object>) dataSnapshot.getValue());
                        collectFood((Map<String,Object>) dataSnapshot.getValue());
                        collectTravel((Map<String,Object>) dataSnapshot.getValue());
                        findCharacteristic(sportList,movieList,musicList,bookList,foodList,travelList);
                        if(weightage.size()!=0){
                            bubbleSort();
                            count=0;
                            int counter=0;
                            for(int i=0;i<weightage.size();i++){
                                if(filter.get(i)){
                                    textView.append("\n"+nameNearby.get(i));
                                    textView3.append("\n"+genderNearby.get(i));
                                    textView1.append("\n"+weightage.get(i));
                                    contactsFound addition=new contactsFound(nameNearby.get(i), idNearby.get(i),genderNearby.get(i));
                                    name[counter]=nameNearby.get(i);
                                    uid[counter]=idNearby.get(i);
                                    gender[counter]=genderNearby.get(i);
                                    counter++;
                                    count++;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        Button helpBTN=findViewById(R.id.helpButton);
        helpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change=new Intent(getApplicationContext(),contacts.class);
                Log.d("problematic",Integer.toString(count));
                change.putExtra("count",count);
                change.putExtra("name",name);
                change.putExtra("uid",uid);
                change.putExtra("gender",gender);
                startActivity(change);
            }
        });
    }

    private void collectLatitude(Map<Double,Object> UserLocation){
        for(Map.Entry<Double,Object> entry:UserLocation.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId) {
                latitudeList.add((Double) (singleUser.get("latitude")));
            }
        }
    }

    private void collectLongitude(Map<Double,Object> UserLocation){
        for(Map.Entry<Double,Object> entry:UserLocation.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId) {
                longitudeList.add((Double) (singleUser.get("longitude")));
            }
        }
    }

    private void collectName(Map<String,Object> UserLocation){
        for(Map.Entry<String,Object> entry:UserLocation.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId) {
                nameList.add((String) (singleUser.get("name")));
            }
        }
    }

    private void collectId(Map<String,Object> UserLocation){
        for(Map.Entry<String,Object> entry:UserLocation.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId) {
                idList.add((String) (singleUser.get("id")));
            }
        }
    }

    private void collectGender(Map<String,Object> UserLocation){
        for(Map.Entry<String,Object> entry:UserLocation.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId) {
                genderList.add((String) (singleUser.get("gender")));
            }
        }
    }

    public void calcDistance(LinkedList latitudeList, LinkedList longitudeList){
        for(int i=0;i<latitudeList.size();i++){
            double ans=Math.sqrt(Math.pow(currentLatitude-(Double) latitudeList.get(i),2)+Math.pow(currentLongitude-(Double) longitudeList.get(i),2));
            distance.add(ans);
        }
    }

    private void collectSport(Map<String,Object> User){
        for(Map.Entry<String,Object> entry:User.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId2) {
                sportList.add((String) (singleUser.get("sport")));
            }
        }
    }

    private void collectMovie(Map<String,Object> User){
        for(Map.Entry<String,Object> entry:User.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId2) {
                movieList.add((String) (singleUser.get("movie")));
            }
        }
    }

    private void collectMusic(Map<String,Object> User){
        for(Map.Entry<String,Object> entry:User.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId2) {
                musicList.add((String) (singleUser.get("music")));
            }
        }
    }

    private void collectBook(Map<String,Object> User){
        for(Map.Entry<String,Object> entry:User.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId2) {
                bookList.add((String) (singleUser.get("book")));
            }
        }
    }

    private void collectFood(Map<String,Object> User){
        for(Map.Entry<String,Object> entry:User.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId2) {
                foodList.add((String) (singleUser.get("food")));
            }
        }
    }

    private void collectTravel(Map<String,Object> User){
        for(Map.Entry<String,Object> entry:User.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if(singleUser.get("id")!=currentId2) {
                travelList.add((String) (singleUser.get("travel")));
            }
        }
    }

    private void findCharacteristic(LinkedList sportList, LinkedList movieList, LinkedList musicList, LinkedList bookList, LinkedList foodList, LinkedList travelList){
        for(int i=0;i<idList.size();i++){
            boolean isNearbyId=false;
            for(int j=0;j<idNearby.size();j++){
                if(idList.get(i).equals(idNearby.get(j))){
                    isNearbyId=true;
                    break;
                }
            }
            if(isNearbyId){
                int count=0;
                if(sportList.get(i).equals(currentSport)){
                    count++;
                }
                if(movieList.get(i).equals(currentMovie)){
                    count++;
                }
                if(musicList.get(i).equals(currentMusic)){
                    count++;
                }
                if(bookList.get(i).equals(currentBook)){
                    count++;
                }
                if(foodList.get(i).equals(currentFood)){
                    count++;
                }
                if(travelList.get(i).equals(currentTravel)){
                    count++;
                }
                weightage.add(count);
            }
        }
        for(int k=0;k<genderNearby.size();k++){
            if(genderNearby.get(k).equals(currentgenderInterested)){
                filter.add(true);
            }else if(currentgenderInterested.equals("Prefer not to say")){
                filter.add(true);
            }
            else{
                filter.add(false);
            }
        }
    }

    public void swap(int a,int b){
        String temp=nameNearby.get(a);
        nameNearby.set(a,nameNearby.get(b));
        nameNearby.set(b,temp);
        String temp1=genderNearby.get(a);
        genderNearby.set(a,genderNearby.get(b));
        genderNearby.set(b,temp1);
        int temp2=weightage.get(a);
        weightage.set(a,weightage.get(b));
        weightage.set(b,temp2);
        String tempUID=idNearby.get(a);
        idNearby.set(a, idNearby.get(b));
        idNearby.set(b,tempUID);
        boolean temp3=filter.get(a);
        filter.set(a,filter.get(b));
        filter.set(b,temp3);
    }

    public void bubbleSort(){
        for(int i=0;i<weightage.size()-1;i++){
            for(int j=0;j<weightage.size()-1-i;j++){
                if(weightage.get(j).compareTo(weightage.get(j+1))<0){
                    swap(j,j+1);
                }
            }
        }
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
