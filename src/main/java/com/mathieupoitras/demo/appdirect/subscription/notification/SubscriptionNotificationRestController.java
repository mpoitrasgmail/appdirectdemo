package com.mathieupoitras.demo.appdirect.subscription.notification;


import com.mathieupoitras.demo.account.Account;
import com.mathieupoitras.demo.account.AccountRepository;
import com.mathieupoitras.demo.account.AccountType;
import com.mathieupoitras.demo.appdirect.common.BasicResult;
import com.mathieupoitras.demo.appdirect.subscription.client.SubscriptionEventsClient;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionCancel;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionChange;
import com.mathieupoitras.demo.appdirect.subscription.model.SubscriptionOrder;
import com.mathieupoitras.demo.appdirect.subscription.notification.event.SubscriptionCreateEventResult;
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
@RequestMapping("/appdirect/subscription/notification")
public class SubscriptionNotificationRestController {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionNotificationRestController.class);

    private final AccountRepository accountRepository;
    private final SubscriptionEventsClient subscriptionEventsClient;

    @Autowired
    public SubscriptionNotificationRestController(AccountRepository accountRepository, SubscriptionEventsClient subscriptionEventsClient) {
        this.accountRepository = accountRepository;
        this.subscriptionEventsClient = subscriptionEventsClient;
    }

    @RequestMapping(method = RequestMethod.GET, value="/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResult create(@RequestParam("eventUrl") String appDirectEventUrlValue) {
        logger.info("Subscription Create Event received");
        SubscriptionOrder subscriptionOrder = subscriptionEventsClient.retrieveSubscriptionOrderFrom(appDirectEventUrlValue);
        AccountType accountType = AccountType.valueOf(subscriptionOrder.getOrder().getEditionCode());
        Account newAccount = new Account(accountType);

        Account newlyCreatedAccount = accountRepository.save(newAccount);
        return new SubscriptionCreateEventResult(newlyCreatedAccount.getAccountUid());
    }

    @RequestMapping(method = RequestMethod.GET, value="/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResult change(@RequestParam("eventUrl") String appDirectEventUrlValue) {
        logger.info("Subscription Change Event received");
        SubscriptionChange subscriptionChange = subscriptionEventsClient.retrieveSubscriptionChangeFrom(appDirectEventUrlValue);

        String accountUid = subscriptionChange.getAccount().getAccountIdentifier();
        Account existingAccount = accountRepository.findByAccountUid(accountUid);

        AccountType accountType = AccountType.valueOf(subscriptionChange.getOrder().getEditionCode());
        existingAccount.setAccountType(accountType);

        accountRepository.save(existingAccount);
        return new BasicResult();
    }

    @RequestMapping(method = RequestMethod.GET, value="/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResult cancel(@RequestParam("eventUrl") String appDirectEventUrlValue) {
        logger.info("Subscription Cancel Event received");
        SubscriptionCancel subscriptionCancel = subscriptionEventsClient.retrieveSubscriptionCancelFrom(appDirectEventUrlValue);
        String accountUid = subscriptionCancel.getAccount().getAccountIdentifier();
        Account existingAccount = accountRepository.findByAccountUid(accountUid);

        accountRepository.delete(existingAccount);
        return new BasicResult();
    }
}
