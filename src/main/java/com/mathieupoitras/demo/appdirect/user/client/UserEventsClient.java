package com.mathieupoitras.demo.appdirect.user.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Mathieu Poitras on 2016-06-21.
 */
@Service
public class UserEventsClient {

    private final RestTemplate restTemplate;

    public UserEventsClient() {
        restTemplate = new RestTemplate();
    }
    /**
     * Only used for testing so that the rest template can be mocked
     */
    UserEventsClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public UserAssignment retrieveUserAssignmentFrom(String retrievalUrl) {
        UserAssignment userAssignment = restTemplate.getForObject(retrievalUrl, UserAssignment.class);
        return userAssignment;
    }

    public UserUnassignment retrieveUserUnassignmentFrom(String retrievalUrl) {
        UserUnassignment userUnassignment = restTemplate.getForObject(retrievalUrl, UserUnassignment.class);
        return userUnassignment;
    }
}
