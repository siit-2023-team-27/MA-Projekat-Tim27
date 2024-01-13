package com.example.nomad.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.nomad.enums.UserType;


import java.util.List;

public class UserDTO implements Parcelable {
        private Long id;
        private String firstName;
        private String lastName;
        private String address;
        private String username;
        private String password;
        private String phoneNumber;
        private boolean suspended;
        private boolean verified;
        private List<UserType> roles;

    // Constructor
    public UserDTO(Long id, String firstName, String lastName, String address, String username, String password, String phoneNumber,  List<UserType> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.suspended = false;
        this.verified = true;
        this.roles = roles;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<UserType> getRoles() {
        return roles;
    }

    public void setRoles(List<UserType> roles) {
        this.roles = roles;
    }

    public UserDTO(){}
    // Getters and setters for each attribute

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void suspend(){
        this.suspended = true;
    }
    public void unsuspend(){
        this.suspended = false;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(username);
        dest.writeString(address);
        dest.writeString(phoneNumber);
    }
}
