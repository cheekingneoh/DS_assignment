package com.DSAssignment.FriendZone.userLogInStuff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.DSAssignment.FriendZone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUsingOtherAcc extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail;
    EditText editTextPassword;
    TextView signUpFZ;
    TextView signInFZ;
    Button buttonLogin;
    FirebaseAuth auth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_using_other_acc);

        editTextEmail=findViewById(R.id.editTextForEmailOtherAcc);
        editTextPassword=findViewById(R.id.editTextForPasswordOtherAcc);
        signUpFZ=findViewById(R.id.textViewSignUpFriendZoneAcc);
        signInFZ=findViewById(R.id.textViewSignInFrindZoneAcc);
        buttonLogin=findViewById(R.id.buttonForLogInOtherAcc);

        buttonLogin.setOnClickListener(this);
        signUpFZ.setOnClickListener(this);
        signInFZ.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        if(v==signUpFZ){
            finish();
            startActivity(new Intent(this, SignUp.class));
            return;
        }

        if(v==signInFZ){
            finish();
            startActivity(new Intent(this, login.class));
            return;
        }

      if(v==buttonLogin){
          loggingInOtherAcc();

      }


    }


    public void loggingInOtherAcc(){
        String email=editTextEmail.getText().toString();
        String password=editTextPassword.getText().toString();
        OtherAccAdapter tantan=new OtherAccAdapter(new OtherAcc(email,password));
       if(tantan.checkAuthentication()) {
           auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(LoginUsingOtherAcc.this,"Welcome",Toast.LENGTH_SHORT).show();
                       finish();
                       startActivity(new Intent(getApplicationContext(), infoUpdate.class));
                   }
                   else{
                       finish();
                   startActivity(new Intent(getApplicationContext(), infoUpdate.class));}
               }
           });

       }
       else
           Toast.makeText(this,"Please make sure you have registered.",Toast.LENGTH_SHORT).show();
       return;
    }


}
