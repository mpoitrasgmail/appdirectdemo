package com.mathieupoitras.demo.appdirect.subscription.client;

import com.mathieupoitras.demo.account.AccountType;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionCancel;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionCancelBuilder;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionChange;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionChangeBuilder;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionOrder;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionOrderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
@Service
@Profile("appdirectStubs")
public class SubscriptionEventsClientStub implements SubscriptionEventsClient {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionEventsClientStub.class);
    public static final String ACCOUNT_IDENTIFIER_ABSENT = "ACCOUNT_IDENTIFIER_ABSENT";

    private final RestTemplate restTemplate;

    public SubscriptionEventsClientStub() {
        restTemplate = new RestTemplate();
    }


    public SubscriptionOrder retrieveSubscriptionOrderFrom(String retrievalUrl) {
        logger.debug("Generating Subscription Order for " + retrievalUrl);
        SubscriptionOrder subscriptionOrder = SubscriptionOrderBuilder.init()
                .withOrderEditionCode(AccountType.FREE.name())
                .build();
        return subscriptionOrder;
    }

    public SubscriptionChange retrieveSubscriptionChangeFrom(String retrievalUrl) {
        logger.debug("Generating Subscription Change for " + retrievalUrl);
        SubscriptionChange subscriptionChange = SubscriptionChangeBuilder.init()
                .withAccountIdentifier(determineAccountIdentifier(retrievalUrl))
                .withOrderEditionCode(AccountType.PREMIUM.name())
                .build();
        return subscriptionChange;
    }

    private String determineAccountIdentifier(String retrievalUrl) {
        String[] urlSplit = retrievalUrl.split("stubAccountIdentifier=");
        if(urlSplit.length == 2){
            String accountIdentifier = urlSplit[1];
            return accountIdentifier;
        }
        return ACCOUNT_IDENTIFIER_ABSENT;
    }

    public SubscriptionCancel retrieveSubscriptionCancelFrom(String retrievalUrl) {
        logger.debug("Generating Subscription Cancel for " + retrievalUrl);
        SubscriptionCancel subscriptionCancel = SubscriptionCancelBuilder.init()
                .withAccountIdentifier(determineAccountIdentifier(retrievalUrl))
                .build();
        return subscriptionCancel;
    }
}
