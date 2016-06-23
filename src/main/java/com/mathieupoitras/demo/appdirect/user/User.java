package com.mathieupoitras.demo.appdirect.user;

/**
 * Created by Mathieu Poitras on 2016-06-21.
 */
public class User {
    private String email;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
