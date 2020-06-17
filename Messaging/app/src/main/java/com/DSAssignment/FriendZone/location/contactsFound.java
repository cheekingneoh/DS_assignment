package com.DSAssignment.FriendZone.location;

public class contactsFound {

    private String name;
    private String uid;
    private String gender;

    public contactsFound(String n, String u, String g){
        this.name=n;
        this.uid=u;
        this.gender=g;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
