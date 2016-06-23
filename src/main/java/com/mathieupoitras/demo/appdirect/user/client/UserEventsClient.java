package com.mathieupoitras.demo.appdirect.user.client;

/**
 * Created by Mathieu Poitras on 2016-06-21.
 */
public interface UserEventsClient {
    UserAssignment retrieveUserAssignmentFrom(String retrievalUrl);
    UserUnassignment retrieveUserUnassignmentFrom(String retrievalUrl);
}
