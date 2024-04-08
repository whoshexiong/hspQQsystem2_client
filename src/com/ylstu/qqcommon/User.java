package com.ylstu.qqcommon;

import java.io.Serializable;

/**
 * @author YI
 * @version 1.0
 */
public class User implements Serializable {
    String username;

    String passwd;

    public Boolean isOnline = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public User(String username, String passwd) {
        this.username = username;
        this.passwd = passwd;
    }
}
