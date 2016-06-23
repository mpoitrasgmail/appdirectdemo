package com.mathieupoitras.demo.appdirect.user.client;

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
public class UserEventsClientStub implements UserEventsClient {

    private static final Logger logger = LoggerFactory.getLogger(UserEventsClientStub.class);

    public static final String ACCOUNT_IDENTIFIER_ABSENT = "ACCOUNT_IDENTIFIER_ABSENT";
    public static final String USER_UUID_ABSENT = "USER_UUID_ABSENT";
    public static final String USER_EMAIL_ABSENT = "USER_EMAIL_ABSENT";

    @Override
    public UserAssignment retrieveUserAssignmentFrom(String retrievalUrl) {
        logger.debug("Generating User Assignment for " + retrievalUrl);
        List<NameValuePair> queryParameters = extractQueryParameters(retrievalUrl);
        UserAssignment userAssignment = UserAssignmentBuilder.init()
                .withAccountIdentifier(determineAccountIdentifier(queryParameters))
                .withUserUuid(determineUserUuid(queryParameters))
                .withUserEmail(determineUserEmail(queryParameters))
                .build();
        return userAssignment;
    }

    @Override
    public UserUnassignment retrieveUserUnassignmentFrom(String retrievalUrl) {
        logger.debug("Generating User Unassignment for " + retrievalUrl);
        List<NameValuePair> queryParameters = extractQueryParameters(retrievalUrl);
        UserUnassignment userUnassignment = UserUnassignmentBuilder.init()
                .withAccountIdentifier(determineAccountIdentifier(queryParameters))
                .withUserUuid(determineUserUuid(queryParameters))
                .withUserEmail(determineUserEmail(queryParameters))
                .build();
        return userUnassignment;
    }

    private List<NameValuePair> extractQueryParameters(String retrievalUrl) {
        try {
            List<NameValuePair> queryParameters = URLEncodedUtils.parse(new URI(retrievalUrl), "UTF-8");
            return queryParameters;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String determineUserEmail(List<NameValuePair> queryParameters) {
        return determineFromUrl(queryParameters, "stubUserEmail", USER_EMAIL_ABSENT);
    }

    private String determineUserUuid(List<NameValuePair> queryParameters) {
        return determineFromUrl(queryParameters, "stubUserUuid", USER_UUID_ABSENT);
    }

    private String determineAccountIdentifier(List<NameValuePair> queryParameters) {
        return determineFromUrl(queryParameters, "stubAccountIdentifier", ACCOUNT_IDENTIFIER_ABSENT);
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
