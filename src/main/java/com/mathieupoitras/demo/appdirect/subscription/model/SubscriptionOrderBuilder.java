package com.mathieupoitras.demo.appdirect.subscription.model;


/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class SubscriptionOrderBuilder {
    private boolean needsOrderObject;
    private String orderEditionCode;

    public static SubscriptionOrderBuilder init() {
        return new SubscriptionOrderBuilder();
    }

    public SubscriptionOrderBuilder withOrderEditionCode(String orderEditionCode) {
        needsOrderObject = true;
        this.orderEditionCode = orderEditionCode;
        return this;
    }

    public SubscriptionOrder build() {
        SubscriptionOrder subscriptionOrder = new SubscriptionOrder();
        if(needsOrderObject){
            Order order = new Order();
            order.setEditionCode(orderEditionCode);
            subscriptionOrder.setOrder(order);
        }
        return subscriptionOrder;
    }
}
