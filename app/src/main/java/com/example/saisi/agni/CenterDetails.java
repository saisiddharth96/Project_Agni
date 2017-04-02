package com.example.saisi.agni;

/**
 * Created by saisi on 02-Apr-17.
 */

public class CenterDetails {
    String SignInCenterName;
    String Address;
    String Timings;


    public CenterDetails(String signInCenterName, String address, String timings) {
        SignInCenterName = signInCenterName;

        Address = address;
        Timings = timings;
    }

    public String getSignInCenterName() {
        return SignInCenterName;
    }

    public String getAddress() {
        return Address;
    }

    public String getTimings() {
        return Timings;
    }
}
