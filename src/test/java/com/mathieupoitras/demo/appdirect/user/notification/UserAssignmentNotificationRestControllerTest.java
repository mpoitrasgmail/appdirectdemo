package com.mathieupoitras.demo.appdirect.user.notification;

import com.mathieupoitras.demo.account.Account;
import com.mathieupoitras.demo.account.AccountRepository;
import com.mathieupoitras.demo.account.user.User;
import com.mathieupoitras.demo.account.user.UserRepository;
import com.mathieupoitras.demo.appdirect.user.client.UserAssignment;
import com.mathieupoitras.demo.appdirect.user.client.UserAssignmentBuilder;
import com.mathieupoitras.demo.appdirect.user.client.UserEventsClient;
import com.mathieupoitras.demo.appdirect.user.client.UserUnassignment;
import com.mathieupoitras.demo.appdirect.user.client.UserUnassignmentBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Mathieu Poitras on 2016-06-22.
 */
public class UserAssignmentNotificationRestControllerTest {

    @Mock
    private AccountRepository accountRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private UserEventsClient userEventsClientMock;

    @InjectMocks
    private UserAssignmentNotificationRestController controllerUnderTest;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(controllerUnderTest).build();
    }

    @Test
    public void whenAssigningAUserThenItIsAddedToTheAccount() throws Exception {
        String appDirectEventUrlValue = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";
        String accountUid = "accountUid";
        String userUid = "userUid";
        String userEmail = "user_email@hello.com";
        UserAssignment userAssignment = UserAssignmentBuilder.init()
                .withAccountIdentifier(accountUid)
                .withUserUuid(userUid)
                .withUserEmail(userEmail)
                .build();
        when(userEventsClientMock.retrieveUserAssignmentFrom(appDirectEventUrlValue)).thenReturn(userAssignment);

        Account existingAccount = mock(Account.class);
        when(accountRepositoryMock.findByAccountUid(accountUid)).thenReturn(existingAccount);

        User userToBeAssigned = new User();
        when(userRepositoryMock.findByUserUid(userUid)).thenReturn(userToBeAssigned);

        mockMvc.perform(get("/appdirect/user/notification/assign?eventUrl=" + appDirectEventUrlValue)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(status().isOk());

        verify(existingAccount).addUser(userToBeAssigned);
        verify(accountRepositoryMock).save(existingAccount);
    }

    @Test
    public void whenUnassigningAUserThenItIsRemovedFromTheAccount() throws Exception {
        String appDirectEventUrlValue = "https://www.acme-marketplace.com/api/integration/v1/events/d15bb36e-5fb5-11e0-8c3c-00262d2cda03";
        String accountUid = "accountUid";
        String userUid = "userUid";
        String userEmail = "user_email@hello.com";
        UserUnassignment userAssignment = UserUnassignmentBuilder.init()
                .withAccountIdentifier(accountUid)
                .withUserUuid(userUid)
                .withUserEmail(userEmail)
                .build();
        when(userEventsClientMock.retrieveUserUnassignmentFrom(appDirectEventUrlValue)).thenReturn(userAssignment);


        Account existingAccount = mock(Account.class);
        when(accountRepositoryMock.findByAccountUid(accountUid)).thenReturn(existingAccount);

        User userToBeUnassigned = new User();
        when(userRepositoryMock.findByUserUid(userUid)).thenReturn(userToBeUnassigned);

        mockMvc.perform(get("/appdirect/user/notification/unassign?eventUrl=" + appDirectEventUrlValue)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(status().isOk());

        verify(existingAccount).removeUser(userToBeUnassigned);
        verify(accountRepositoryMock).save(existingAccount);
    }

}