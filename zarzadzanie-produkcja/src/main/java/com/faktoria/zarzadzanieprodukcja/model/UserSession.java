package com.faktoria.zarzadzanieprodukcja.model;

import org.springframework.stereotype.Component;


@Component
public class UserSession {
    private Long userId;
    private String username;
    private boolean loggedIn = false;

    // Gettery i settery
    public boolean isUserLoggedIn() {
        return loggedIn;
    }

    public void setUserLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void loginUser(Long userId, String username) {
        this.userId = userId;
        this.username = username;
        this.loggedIn = true;
    }

    public void logoutUser() {
        this.userId = null;
        this.username = null;
        this.loggedIn = false;
    }
}
