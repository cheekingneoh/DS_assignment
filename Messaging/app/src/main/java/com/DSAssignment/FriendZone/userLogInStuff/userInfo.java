package com.DSAssignment.FriendZone.userLogInStuff;

public class userInfo {
    public String email;
    public String name;
    public String id;
    public int age;
    public String description;
    public String gender;
    public String sport;
    public String movie;
    public String music;
    public String book;
    public String food;
    public String travel;
    public String genderInterested;

    public userInfo() {


    }

    public userInfo(String name, String id, String email, int age,String gender, String description, String sport, String movie, String music, String book, String food, String travel, String genderInterested) {
        this.name=name;
        this.id=id;
        this.email = email;
        this.age = age;
        this.gender=gender;
        this.description = description;
        this.sport = sport;
        this.movie = movie;
        this.music = music;
        this.book = book;
        this.food = food;
        this.travel = travel;
        this.genderInterested = genderInterested;
    }


}
