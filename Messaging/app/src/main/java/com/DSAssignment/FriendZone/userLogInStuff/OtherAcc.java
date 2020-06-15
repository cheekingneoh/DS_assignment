package com.DSAssignment.FriendZone.userLogInStuff;


import java.util.LinkedList;


public class OtherAcc  {
    LinkedList<String> ls=new LinkedList<>();


    String email;
    String password;


    public OtherAcc() {
    }

    public OtherAcc(String email, String password) {
        ls.add("tantan@gmail.com");
        ls.add("1234567890");
        ls.add("tinder@gmail.com");
        ls.add("tinder12345");
        this.email = email;
        this.password = password;
    }


  public boolean login(){

        if(ls.contains(email)&&ls.contains(password)){
           if(ls.indexOf(password)-ls.indexOf(email)==1)
               return true;

        }

        return false;


  }
}
