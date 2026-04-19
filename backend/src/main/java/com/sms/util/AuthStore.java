package com.sms.util;

import org.springframework.stereotype.Component;

/**
 * Simple authentication store for admin credentials.
 * OOP — ENCAPSULATION
 */
@Component
public class AuthStore {
    private String username = "ucanaryn";
    private String password = "naryncampus";

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
