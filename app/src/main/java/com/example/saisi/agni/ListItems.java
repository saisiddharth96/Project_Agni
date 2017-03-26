package com.example.saisi.agni;

/**
 * Created by saisi on 19-Mar-17.
 */

public class ListItems {
    String UserName;
    String CenterLocation;

    public ListItems(String userName, String centerLocation) {
        this.UserName = userName;
        this.CenterLocation = centerLocation;
    }

    public String getUserName() {
        return UserName;
    }

    public String getCenterLocation() {
        return CenterLocation;
    }
}
