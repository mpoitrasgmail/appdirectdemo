package com.mathieupoitras.demo.appdirect.user.notification;

import com.mathieupoitras.demo.account.Account;
import com.mathieupoitras.demo.account.AccountRepository;
import com.mathieupoitras.demo.account.user.User;
import com.mathieupoitras.demo.account.user.UserRepository;
import com.mathieupoitras.demo.appdirect.common.BasicResult;
import com.mathieupoitras.demo.appdirect.user.client.UserAssignment;
import com.mathieupoitras.demo.appdirect.user.client.UserEventsClient;
import com.mathieupoitras.demo.appdirect.user.client.UserUnassignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mathieu Poitras on 2016-06-19.
 */
@RestController
@RequestMapping("/appdirect/user/notification")
public class UserAssignmentNotificationRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserAssignmentNotificationRestController.class);

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserEventsClient userEventsClient;

    @Autowired
    public UserAssignmentNotificationRestController(AccountRepository accountRepository,
                                                    UserRepository userRepository,
                                                    UserEventsClient userEventsClient) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.userEventsClient = userEventsClient;
    }


    @RequestMapping(method = RequestMethod.GET, value="/assign", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResult assign(@RequestParam("eventUrl") String appDirectEventUrlValue) {
        logger.info("User Assignment Event received");
        UserAssignment userAssignment = userEventsClient.retrieveUserAssignmentFrom(appDirectEventUrlValue);
        String accountIdentifier = userAssignment.getAccount().getAccountIdentifier();
        Account existingAccount = accountRepository.findByAccountUid(accountIdentifier);
        if(existingAccount == null){
            return new BasicResult("appdirect.user.assignment.account.notFound", "Account for " + accountIdentifier + " cannot be found");
        }

        com.mathieupoitras.demo.appdirect.user.User appDirectUser = userAssignment.getUser();
        String userUuid = appDirectUser.getUuid();
        User userForUid = userRepository.findByUserUid(userUuid);
        if(userForUid == null){
            User userToBeCreated = new User();
            userToBeCreated.setUsername(appDirectUser.getEmail());
            userToBeCreated.setAccount(existingAccount);

            userForUid = userRepository.save(userToBeCreated);
        }
        existingAccount.addUser(userForUid);
        accountRepository.save(existingAccount);

        return new BasicResult();
    }

    @RequestMapping(method = RequestMethod.GET, value="/unassign", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResult unassign(@RequestParam("eventUrl") String appDirectEventUrlValue) {
        logger.info("User Unassignment Event received");
        UserUnassignment userUnassignment = userEventsClient.retrieveUserUnassignmentFrom(appDirectEventUrlValue);
        String accountIdentifier = userUnassignment.getAccount().getAccountIdentifier();
        Account existingAccount = accountRepository.findByAccountUid(accountIdentifier);
        if(existingAccount == null){
            return new BasicResult("appdirect.user.unassignment.account.notFound", "Account for " + accountIdentifier + " cannot be found");
        }

        com.mathieupoitras.demo.appdirect.user.User appDirectUser = userUnassignment.getUser();
        String userUuid = appDirectUser.getUuid();
        User userForUid = userRepository.findByUserUid(userUuid);
        if(userForUid == null){
            return new BasicResult("appdirect.user.unassignment.user.notFound", "User for " + userUuid + " cannot be found");
        }
        existingAccount.removeUser(userForUid);
        accountRepository.save(existingAccount);

        return new BasicResult();
    }
}
