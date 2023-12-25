package com.example.nomad.enums;


public enum UserType{
    GUEST, HOST, ADMIN;

    public String getAuthority() {
        return this.toString();
    }
}