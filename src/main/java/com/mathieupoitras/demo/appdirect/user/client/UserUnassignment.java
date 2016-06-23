package com.mathieupoitras.demo.appdirect.user.client;


import com.mathieupoitras.demo.appdirect.model.account.Account;
import com.mathieupoitras.demo.appdirect.user.User;

/**
 * Created by Mathieu Poitras on 2016-06-21.
 */
public class UserUnassignment {
    private Account account;
    private User user;

    public Account getAccount() {
        return account;
    }

    public User getUser() {
        return user;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
