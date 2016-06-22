package com.mathieupoitras.demo.appdirect.subscription.notification;


import com.mathieupoitras.demo.appdirect.common.BasicResult;
import com.mathieupoitras.demo.appdirect.subscription.notification.event.SubscriptionCreateEventResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @RequestMapping(method = RequestMethod.GET, value="/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResult create(@RequestParam("eventUrl") String appDirectEventUrlValue) {
        logger.info("Subscription Create Event received");
        return new SubscriptionCreateEventResult("Hard coded");
    }

    @RequestMapping(method = RequestMethod.GET, value="/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResult change(@RequestParam("eventUrl") String appDirectEventUrlValue) {
        logger.info("Subscription Change Event received");
        return new BasicResult();
    }

    @RequestMapping(method = RequestMethod.GET, value="/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
    public BasicResult cancel(@RequestParam("eventUrl") String appDirectEventUrlValue) {
        logger.info("Subscription Cancel Event received");
        return new BasicResult();
    }
}
