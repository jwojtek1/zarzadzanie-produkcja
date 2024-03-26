package com.faktoria.zarzadzanieprodukcja.model;
import org.springframework.stereotype.Component;

@Component
public class UserSession {
    private Long userId;
    private String username;
    private boolean loggedIn = false;

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
}
