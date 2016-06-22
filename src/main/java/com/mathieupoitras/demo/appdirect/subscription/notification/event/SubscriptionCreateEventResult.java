package com.mathieupoitras.demo.appdirect.subscription.notification.event;


import com.mathieupoitras.demo.appdirect.common.BasicResult;

/**
 * Created by Mathieu Poitras on 2016-06-19.
 */
public class SubscriptionCreateEventResult extends BasicResult {
    private String accountIdentifier;

    public SubscriptionCreateEventResult(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }
}
