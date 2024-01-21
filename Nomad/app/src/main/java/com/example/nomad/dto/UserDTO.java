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
        private int cancellationNumber;

    // Constructor
    public UserDTO(Long id, String firstName, String lastName, String address, String username, String password, String phoneNumber,  List<UserType> roles, int cancellationNumber) {
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
        this.cancellationNumber = cancellationNumber;
    }

    public int getCancellationNumber() {
        return cancellationNumber;
    }

    public void setCancellationNumber(int cancellationNumber) {
        this.cancellationNumber = cancellationNumber;
    }

    public UserDTO(Long id, String firstName, String lastName, String address, String username, String password, String phoneNumber, List<UserType> roles) {
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

    protected UserDTO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        firstName = in.readString();
        lastName = in.readString();
        address = in.readString();
        username = in.readString();
        password = in.readString();
        phoneNumber = in.readString();
        suspended = in.readByte() != 0;
        verified = in.readByte() != 0;
        cancellationNumber = in.readInt();
    }

    public static final Creator<UserDTO> CREATOR = new Creator<UserDTO>() {
        @Override
        public UserDTO createFromParcel(Parcel in) {
            return new UserDTO(in);
        }

        @Override
        public UserDTO[] newArray(int size) {
            return new UserDTO[size];
        }
    };

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

        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(address);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(phoneNumber);
        dest.writeByte((byte) (suspended ? 1 : 0));
        dest.writeByte((byte) (verified ? 1 : 0));
        dest.writeInt(cancellationNumber);
    }

}
