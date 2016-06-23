package com.mathieupoitras.demo.appdirect.subscription.client;

import com.mathieupoitras.demo.account.AccountType;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionCancel;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionCancelBuilder;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionChange;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionChangeBuilder;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionOrder;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionOrderBuilder;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
@Service
@Profile("appdirectStubs")
public class SubscriptionEventsClientStub implements SubscriptionEventsClient {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionEventsClientStub.class);
    public static final String ACCOUNT_IDENTIFIER_ABSENT = "ACCOUNT_IDENTIFIER_ABSENT";

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

    public SubscriptionCancel retrieveSubscriptionCancelFrom(String retrievalUrl) {
        logger.debug("Generating Subscription Cancel for " + retrievalUrl);
        SubscriptionCancel subscriptionCancel = SubscriptionCancelBuilder.init()
                .withAccountIdentifier(determineAccountIdentifier(retrievalUrl))
                .build();
        return subscriptionCancel;
    }

    private String determineAccountIdentifier(String retrievalUrl) {
        try {
            List<NameValuePair> queryParameters = URLEncodedUtils.parse(new URI(retrievalUrl), "UTF-8");
            return determineFromUrl(queryParameters, "stubAccountIdentifier", ACCOUNT_IDENTIFIER_ABSENT);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String determineFromUrl(List<NameValuePair> queryParameters, String keyInUrl, String defaultValue) {
        Optional<NameValuePair> firstMatch = queryParameters.stream()
                .filter(x -> keyInUrl.equals(x.getName()))
                .findFirst();
        if(firstMatch.isPresent()){
            return firstMatch.get().getValue();
        }
        return defaultValue;
    }
}
