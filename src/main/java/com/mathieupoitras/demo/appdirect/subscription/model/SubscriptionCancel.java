package com.mathieupoitras.demo.appdirect.subscription.model;


import com.mathieupoitras.demo.appdirect.model.account.Account;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class SubscriptionCancel {
    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
