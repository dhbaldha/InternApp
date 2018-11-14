package com.myinternapp.networking.request;

import com.google.gson.Gson;

public class RequestCredentials {

    private String username, password;

    public RequestCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
