package com.DSAssignment.FriendZone.location;

public class contactCardInfo {
    private String uid;
    private String name;
    private String lastMessage;

    public contactCardInfo(){

    }

    public contactCardInfo(String UID, String n,String LM){
        this.uid=UID;
        this.lastMessage=LM;
        this.name=n;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
