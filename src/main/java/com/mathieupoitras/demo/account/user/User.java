package com.mathieupoitras.demo.account.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mathieupoitras.demo.account.Account;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by Mathieu Poitras on 2016-06-21.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private long id;
    private String userUid;
    private String username;
    @JsonIgnore
    private String passwordHash; //This should never be returned, but should be saved

    @ManyToOne
    @JoinColumn(name = "book_category_id")
    private Account account;

    public User() {
        this.userUid = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public String getPasswordHash() {
        return passwordHash;
    }

    public String getUserUid() {
        return userUid;
    }

}
