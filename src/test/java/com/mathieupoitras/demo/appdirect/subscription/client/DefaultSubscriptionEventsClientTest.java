package com.mathieupoitras.demo.appdirect.subscription.client;

import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionCancel;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionChange;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionOrder;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class DefaultSubscriptionEventsClientTest {

    @Test
    public void whenRetrievingASubscriptionOrderFromAppDirectThenASubcriptionOrderIsRetrieved(){
        RestTemplate restTemplate = mock(RestTemplate.class);
        DefaultSubscriptionEventsClient classUnderTest = new DefaultSubscriptionEventsClient(restTemplate);
        String retrievalUrl = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";
        when(restTemplate.getForObject(retrievalUrl, SubscriptionOrder.class)).thenReturn(mock(SubscriptionOrder.class));


        SubscriptionOrder subscriptionOrder = classUnderTest.retrieveSubscriptionOrderFrom(retrievalUrl);


        assertThat(subscriptionOrder, is(not(nullValue())));
    }

    @Test
    public void whenRetrievingASubscriptionChangeFromAppDirectThenASubcriptionChangeIsRetrieved(){
        RestTemplate restTemplate = mock(RestTemplate.class);
        DefaultSubscriptionEventsClient classUnderTest = new DefaultSubscriptionEventsClient(restTemplate);
        String retrievalUrl = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";
        when(restTemplate.getForObject(retrievalUrl, SubscriptionChange.class)).thenReturn(mock(SubscriptionChange.class));


        SubscriptionChange subscriptionChange = classUnderTest.retrieveSubscriptionChangeFrom(retrievalUrl);


        assertThat(subscriptionChange, is(not(nullValue())));
    }

    @Test
    public void whenRetrievingASubscriptionCancelFromAppDirectThenASubcriptionChangeIsRetrieved(){
        RestTemplate restTemplate = mock(RestTemplate.class);
        DefaultSubscriptionEventsClient classUnderTest = new DefaultSubscriptionEventsClient(restTemplate);
        String retrievalUrl = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";
        when(restTemplate.getForObject(retrievalUrl, SubscriptionCancel.class)).thenReturn(mock(SubscriptionCancel.class));


        SubscriptionCancel subscriptionCancel = classUnderTest.retrieveSubscriptionCancelFrom(retrievalUrl);


        assertThat(subscriptionCancel, is(not(nullValue())));
    }

}