package com.example.hellodemo;

import java.io.Serializable;

/**
 * Created by tong on 16/4/20.
 */
public class UserInfo implements Serializable {
    private String user;
    private String password;

    public UserInfo(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
