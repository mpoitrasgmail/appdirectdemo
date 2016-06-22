package com.mathieupoitras.demo.appdirect.subscription.notification;

import com.mathieupoitras.demo.account.Account;
import com.mathieupoitras.demo.account.AccountRepository;
import com.mathieupoitras.demo.account.AccountType;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionCancel;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionCancelBuilder;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionChange;
import com.mathieupoitras.demo.appdirect.subscription.client.DefaultSubscriptionEventsClient;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionChangeBuilder;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionOrder;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionOrderBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Mathieu Poitras on 2016-06-22.
 */
public class SubscriptionNotificationRestControllerTest {

    @Mock
    private AccountRepository accountRepositoryMock;
    @Mock
    private DefaultSubscriptionEventsClient subscriptionEventsClientMock;

    @InjectMocks
    private SubscriptionNotificationRestController controllerUnderTest;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void whenGettingASubscriptionCreateNotificationThenAnAccountIsCreatedWithTheCorrectType() throws Exception {
        String appDirectEventUrlValue = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";

        AccountType expectedAccountType = AccountType.PREMIUM;
        SubscriptionOrder subscriptionOrder = SubscriptionOrderBuilder.init()
                .withOrderEditionCode(expectedAccountType.name())
                .build();
        when(subscriptionEventsClientMock.retrieveSubscriptionOrderFrom(appDirectEventUrlValue)).thenReturn(subscriptionOrder);

        String expectedAccountIdentifier = "new-account-identifier";
        Account account = new Account(expectedAccountIdentifier);
        when(accountRepositoryMock.save(any(Account.class))).thenReturn(account);

        mockMvc.perform(get("/appdirect/subscription/notification/create?eventUrl=" + appDirectEventUrlValue)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountIdentifier").isString())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGettingASubscriptionChangeNotificationThenAnAccountIsModifiedWithTheCorrectAccountType() throws Exception {
        String appDirectEventUrlValue = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";

        AccountType expectedAccountType = AccountType.PREMIUM;
        String existingAccountUid = "ABCD-ABCD-ABCD-ABCD";
        SubscriptionChange subscriptionChange = SubscriptionChangeBuilder.init()
                .withOrderEditionCode(expectedAccountType.name())
                .withAccountIdentifier(existingAccountUid)
                .build();
        when(subscriptionEventsClientMock.retrieveSubscriptionChangeFrom(appDirectEventUrlValue)).thenReturn(subscriptionChange);

        Account existingAccount = mock(Account.class);
        when(accountRepositoryMock.findByAccountUid(subscriptionChange.getAccount().getAccountIdentifier())).thenReturn(existingAccount);
        when(accountRepositoryMock.save(any(Account.class))).thenReturn(existingAccount);

        mockMvc.perform(get("/appdirect/subscription/notification/change?eventUrl=" + appDirectEventUrlValue)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(status().isOk());

        verify(existingAccount).setAccountType(expectedAccountType);
    }

    @Test
    public void whenGettingASubscriptionCancelNotificationThenTheAccountIsDeleted() throws Exception {
        String appDirectEventUrlValue = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";
        String existingAccountUid = "ABCD-ABCD-ABCD-ABCD";
        SubscriptionCancel subscriptionCancel = SubscriptionCancelBuilder.init()
                .withAccountIdentifier(existingAccountUid)
                .build();
        when(subscriptionEventsClientMock.retrieveSubscriptionCancelFrom(appDirectEventUrlValue)).thenReturn(subscriptionCancel);

        Account existingAccount = mock(Account.class);
        when(accountRepositoryMock.findByAccountUid(subscriptionCancel.getAccount().getAccountIdentifier())).thenReturn(existingAccount);

        mockMvc.perform(get("/appdirect/subscription/notification/cancel?eventUrl=" + appDirectEventUrlValue)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(status().isOk());

        verify(accountRepositoryMock).delete(existingAccount);
    }

}