package com.mathieupoitras.demo.appdirect.subscription.model;


import com.mathieupoitras.demo.appdirect.model.account.Account;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class SubscriptionChange {
    private Order order;
    private Account account;

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
}
