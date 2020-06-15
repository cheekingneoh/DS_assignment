package com.DSAssignment.FriendZone.location;

import com.DSAssignment.FriendZone.userLogInStuff.User;

public class UserLocation extends User {

    double latitude;
    double longitude;
    String name;

    public UserLocation(){

    }

    public UserLocation(double latitude, double longitude, String name) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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
}
