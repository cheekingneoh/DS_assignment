package com.DSAssignment.FriendZone.userLogInStuff;

public class userInfo {
    public String email;
    public String name;
    public int age;
    public String description;
    public String gender;

    public userInfo() {


    }

    public userInfo(String email, int age,String gender, String description) {

        this.email = email;
        this.age = age;
        this.gender=gender;
        this.description = description;
    }


}
