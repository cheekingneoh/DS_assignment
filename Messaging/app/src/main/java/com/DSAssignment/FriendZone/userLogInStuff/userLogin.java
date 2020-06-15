package com.DSAssignment.FriendZone.userLogInStuff;



import com.google.firebase.auth.FirebaseAuth;


public class userLogin implements otherPlatform {
    FirebaseAuth auth= FirebaseAuth.getInstance();
    private String email;
    private String password;
    public userLogin() {
    }

    public userLogin(String email,String password) {
        this.email = email;
        this.password=password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean checkAuthentication() {
      if(auth.signInWithEmailAndPassword(email,password).isComplete()){
          auth.signOut();
      return true;
      }
      return false;
    }
    }

