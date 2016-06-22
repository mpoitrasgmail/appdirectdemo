package com.mathieupoitras.demo.appdirect.subscription.model;

import com.mathieupoitras.demo.appdirect.model.account.Account;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class SubscriptionChangeBuilder {
    private boolean needsOrderObject = false;
    private String orderEditionCode;

    private boolean needsAccountObject = false;
    private String accountAccountIdentifier;

    public static SubscriptionChangeBuilder init() {
        return new SubscriptionChangeBuilder();
    }

    public SubscriptionChangeBuilder withOrderEditionCode(String orderEditionCode) {
        needsOrderObject = true;
        this.orderEditionCode = orderEditionCode;
        return this;
    }

    public SubscriptionChangeBuilder withAccountIdentifier(String accountIdentifier) {
        needsAccountObject = true;
        this.accountAccountIdentifier = accountIdentifier;
        return this;
    }

    public SubscriptionChange build() {
        SubscriptionChange subscriptionChange = new SubscriptionChange();
        if(needsOrderObject){
            Order order = new Order();
            order.setEditionCode(orderEditionCode);
            subscriptionChange.setOrder(order);
        }
        if(needsAccountObject){
            Account account = new Account();
            account.setAccountIdentifier(accountAccountIdentifier);
            subscriptionChange.setAccount(account);
        }
        return subscriptionChange;
    }
}
