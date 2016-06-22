package com.mathieupoitras.demo.appdirect.subscription.client;

import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionCancel;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionChange;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionOrder;

/**
 * Created by Mathieu Poitras on 2016-06-22.
 */
public interface SubscriptionEventsClient {
    SubscriptionOrder retrieveSubscriptionOrderFrom(String appDirectEventUrlValue);
    SubscriptionChange retrieveSubscriptionChangeFrom(String appDirectEventUrlValue);
    SubscriptionCancel retrieveSubscriptionCancelFrom(String appDirectEventUrlValue);
}
