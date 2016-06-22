package com.mathieupoitras.demo.appdirect.subscription.client;

import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionCancel;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionChange;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
@Service
@Profile("!appdirectStubs")
public class DefaultSubscriptionEventsClient implements SubscriptionEventsClient {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSubscriptionEventsClient.class);

    private final RestTemplate restTemplate;

    public DefaultSubscriptionEventsClient() {
        restTemplate = new RestTemplate();
    }
    /**
     * Only used for testing so that the rest template can be mocked
     */
    DefaultSubscriptionEventsClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public SubscriptionOrder retrieveSubscriptionOrderFrom(String retrievalUrl) {
        logger.debug("Retrieving Subscription Order from " + retrievalUrl);
        SubscriptionOrder subscriptionOrder = restTemplate.getForObject(retrievalUrl, SubscriptionOrder.class);
        return subscriptionOrder;
    }

    public SubscriptionChange retrieveSubscriptionChangeFrom(String retrievalUrl) {
        logger.debug("Retrieving Subscription Change from " + retrievalUrl);
        SubscriptionChange subscriptionChange = restTemplate.getForObject(retrievalUrl, SubscriptionChange.class);
        return subscriptionChange;
    }

    public SubscriptionCancel retrieveSubscriptionCancelFrom(String retrievalUrl) {
        logger.debug("Retrieving Subscription Cancel from " + retrievalUrl);
        SubscriptionCancel subscriptionCancel = restTemplate.getForObject(retrievalUrl, SubscriptionCancel.class);
        return subscriptionCancel;
    }
}
