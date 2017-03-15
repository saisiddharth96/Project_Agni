package com.example.saisi.agni;

/**
 * Created by saisi on 15-Mar-17.
 */

public class UserDetails {
    private String FirstName;
    private String LastName;
    private String PhoneNumber;

    public UserDetails(String firstName, String lastName, String phoneNumber) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.PhoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
}
