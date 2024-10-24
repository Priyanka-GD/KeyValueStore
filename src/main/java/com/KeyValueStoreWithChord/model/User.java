package com.KeyValueStoreWithChord.model;


public class User {
    private String userId;
    private String name;
    private String address;
    private String email;

    public User (String userId, String name, String address, String email) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
