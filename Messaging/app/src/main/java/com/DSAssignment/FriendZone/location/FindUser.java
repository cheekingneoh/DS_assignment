package com.DSAssignment.FriendZone.location;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.DSAssignment.FriendZone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.Map;

public class FindUser extends AppCompatActivity {

    private DatabaseReference databaseUser;
    private FirebaseAuth auth;
    private String currentUserId;
    private double currentLatitude;
    private double currentLongitude;
    private String currentName;
    final private double MAX=500.0;
    LinkedList<Double> latitudeList=new LinkedList<>();
    LinkedList<Double> longitudeList=new LinkedList<>();
    LinkedList<String> nameList=new LinkedList<>();
    LinkedList<Double> distance=new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_user);
        Button button=findViewById(R.id.button2);
        final TextView textView=findViewById(R.id.textView3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseUser = FirebaseDatabase.getInstance().getReference().child("UserLocation");
                auth=FirebaseAuth.getInstance();
                currentUserId=auth.getCurrentUser().getUid();
                databaseUser.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        currentLatitude=dataSnapshot.child("latitude").getValue(Double.TYPE);
                        currentLongitude=dataSnapshot.child("longitude").getValue(Double.class);
                        currentName=dataSnapshot.child("name").getValue(String.class);
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
                        calcDistance(latitudeList,longitudeList);
                        double currentSearch=100.0;
                        int count=0;
                        while(currentSearch!=MAX){
                            for(int i=0;i<distance.size();i++){
                                if((Double)distance.get(i)<currentSearch){
                                    textView.append("\n"+nameList.get(i));
                                    count++;
                                }
                            }
                            if(count!=0){
                                break;
                            }
                        }
                        if(count==0){
                            textView.append("\nThere is no user around "+MAX+" metres");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void collectLatitude(Map<Double,Object> UserLocation){
        for(Map.Entry<Double,Object> entry:UserLocation.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if((Double)(singleUser.get("latitude"))!=currentLatitude && (Double)(singleUser.get("longitude"))!=currentLongitude && (String)(singleUser.get("name"))!=currentName) {
                latitudeList.add((Double) (singleUser.get("latitude")));
            }
        }
    }

    private void collectLongitude(Map<Double,Object> UserLocation){
        for(Map.Entry<Double,Object> entry:UserLocation.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if((Double)(singleUser.get("latitude"))!=currentLatitude && (Double)(singleUser.get("longitude"))!=currentLongitude && (String)(singleUser.get("name"))!=currentName) {
                longitudeList.add((Double) (singleUser.get("longitude")));
            }
        }
    }

    private void collectName(Map<String,Object> UserLocation){
        for(Map.Entry<String,Object> entry:UserLocation.entrySet()){
            Map singleUser=(Map)entry.getValue();
            if((Double)(singleUser.get("latitude"))!=currentLatitude && (Double)(singleUser.get("longitude"))!=currentLongitude && (String)(singleUser.get("name"))!=currentName) {
                nameList.add((String) (singleUser.get("name")));
            }
        }
    }

    public void calcDistance(LinkedList latitudeList, LinkedList longitudeList){
        for(int i=0;i<latitudeList.size();i++){
            double ans=Math.sqrt(Math.pow(currentLatitude-(Double) latitudeList.get(i),2)+Math.pow(currentLongitude-(Double) longitudeList.get(i),2));
            distance.add(ans);
        }
    }
}
