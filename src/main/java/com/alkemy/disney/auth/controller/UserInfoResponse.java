package com.alkemy.disney.auth.controller;

import java.util.List;

public class UserInfoResponse {

    private String username;
    private List<String> roles;

    public UserInfoResponse(String username, List<String> roles) {

        this.username = username;
        this.roles = roles;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
