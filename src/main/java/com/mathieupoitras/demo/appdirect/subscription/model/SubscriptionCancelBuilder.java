package com.mathieupoitras.demo.appdirect.subscription.model;


import com.mathieupoitras.demo.appdirect.model.account.Account;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class SubscriptionCancelBuilder {
    private boolean needsAccountObject = false;
    private String accountAccountIdentifier;

    public static SubscriptionCancelBuilder init() {
        return new SubscriptionCancelBuilder();
    }

    public SubscriptionCancelBuilder withAccountIdentifier(String accountIdentifier) {
        needsAccountObject = true;
        this.accountAccountIdentifier = accountIdentifier;
        return this;
    }

    public SubscriptionCancel build() {
        SubscriptionCancel subscriptionCancel = new SubscriptionCancel();
        if(needsAccountObject){
            Account account = new Account();
            account.setAccountIdentifier(accountAccountIdentifier);
            subscriptionCancel.setAccount(account);
        }
        return subscriptionCancel;
    }
}
