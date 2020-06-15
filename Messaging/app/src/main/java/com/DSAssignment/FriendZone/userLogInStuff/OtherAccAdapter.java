package com.DSAssignment.FriendZone.userLogInStuff;

public class OtherAccAdapter implements otherPlatform {

    OtherAcc tt;


    public OtherAccAdapter(OtherAcc tt) {

        this.tt=tt;
    }

    @Override
    public boolean checkAuthentication() {
        return tt.login();
    }
}
