package com.DSAssignment.FriendZone.userLogInStuff;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.DSAssignment.FriendZone.location.UserLocation;
import com.DSAssignment.FriendZone.location.contacts;
import com.DSAssignment.FriendZone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class infoUpdate extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    ArrayAdapter adapter;
    Button button;
    ImageView imageView;

    EditText editTextName;
    EditText editTextAge;
    EditText editTextDescription;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference ref;
    ProgressDialog dialog;
    FirebaseUser user;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private DatabaseReference databaseLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_update);

        auth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        ref=database.getInstance().getReference();
        user=auth.getCurrentUser();
        databaseLocation= FirebaseDatabase.getInstance().getReference("UserLocation");



       spinner=findViewById(R.id.genderSpinner);
//        adapter=ArrayAdapter.createFromResource(this,R.array.gen,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
        imageView=findViewById(R.id.profilePic);
        button=findViewById(R.id.buttonForUpdate);
        editTextName=findViewById(R.id.editTextForName);
        editTextAge=findViewById(R.id.editTextForAge);
        editTextDescription=findViewById(R.id.editTextForDes);
        dialog=new ProgressDialog(this);
        imageView.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if (v==button){
            saveInfo();
            startActivity(new Intent(infoUpdate.this, contacts.class));
        }

        else if (v==imageView){
                    Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivity(intent);
        }




    }

    public void saveInfo(){
//
//        auth=FirebaseAuth.getInstance();
//        database=FirebaseDatabase.getInstance();
//        ref=FirebaseDatabase.getInstance().getReference().child("User");
//        user=auth.getCurrentUser();
        //String userID=user.getUid();
        String userEmail=user.getEmail();
        String name=editTextName.getText().toString();
        String ageString=editTextAge.getText().toString();

        String gender=spinner.getSelectedItem().toString();
        String describe=editTextDescription.getText().toString();



        if(TextUtils.isEmpty(name)){
            editTextName.setError("Cannot be empty");
           editTextName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(ageString)){
            editTextAge.setError("Cannot be empty");
            editTextAge.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(describe)){
            editTextDescription.setError("Cannot be empty");
            editTextDescription.requestFocus();
            return;
        }

        int age=Integer.parseInt(ageString);

        userInfo info=new userInfo(userEmail,age,gender,describe);


        dialog.setMessage("Saving");
        dialog.show();
        FirebaseUser user=auth.getCurrentUser();

        ref.child("User").child(user.getUid().toString()).setValue(info).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(infoUpdate.this,"Updated.",Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(infoUpdate.this, contacts.class));
                }
                else
                    Toast.makeText(infoUpdate.this,"Failed.",Toast.LENGTH_SHORT).show();
            }
        });


    }

}
