package com.DSAssignment.FriendZone.userLogInStuff;

public class userInfo {
    private String email;
    private String name;
    private String id;
    private int age;
    private String description;
    private String gender;
    private String sport;
    private String movie;
    private String music;
    private String book;
    private String food;
    private String travel;
    private String genderInterested;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getGenderInterested() {
        return genderInterested;
    }

    public void setGenderInterested(String genderInterested) {
        this.genderInterested = genderInterested;
    }
}
