package com.DSAssignment.FriendZone.location;

import com.DSAssignment.FriendZone.userLogInStuff.userInfo;

public class UserLocation extends userInfo {

    double latitude;
    double longitude;
    String name;
    String id;
    String gender;

    public UserLocation(){

    }

    public UserLocation(double latitude, double longitude, String name, String id, String gender) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.gender = gender;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }
}
