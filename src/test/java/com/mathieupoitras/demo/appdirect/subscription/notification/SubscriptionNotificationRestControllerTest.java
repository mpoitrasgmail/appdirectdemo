package com.mathieupoitras.demo.appdirect.subscription.notification;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Mathieu Poitras on 2016-06-22.
 */
public class SubscriptionNotificationRestControllerTest {

    @InjectMocks
    private SubscriptionNotificationRestController controllerUnderTest;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void whenGettingASubscriptionCreateNotificationThenAnAccountIsCreated() throws Exception {
        String appDirectEventUrlValue = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";

        mockMvc.perform(get("/appdirect/subscription/notification/create?eventUrl=" + appDirectEventUrlValue)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountIdentifier").isString())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGettingASubscriptionChangeNotificationThenAnAccountIsModified() throws Exception {
        String appDirectEventUrlValue = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";

        mockMvc.perform(get("/appdirect/subscription/notification/change?eventUrl=" + appDirectEventUrlValue)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGettingASubscriptionCancelNotificationThenTheAccountIsDeleted() throws Exception {
        String appDirectEventUrlValue = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";

        mockMvc.perform(get("/appdirect/subscription/notification/cancel?eventUrl=" + appDirectEventUrlValue)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(status().isOk());
    }

}