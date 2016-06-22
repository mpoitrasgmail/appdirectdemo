package com.mathieupoitras.demo.appdirect.security;

import java.time.LocalDateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Mathieu Poitras on 2016-06-20.
 */
public class OAuthSignatureAuthenticationProviderTest {

    public static final String CONSUMER_KEY = "dummy key";
    public static final String CONSUMER_SECRET = "dummy secret";

    @Test
    public void whenValidatingTimestampAndItIsOlderThan10MinutesThenItIsInvalid() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);


        boolean validationResult = oAuthSignatureAuthenticationProvider.validateTimestamp(LocalDateTime.now().minusMinutes(11));


        assertThat(validationResult, is(false));
    }

    @Test
    public void whenValidatingTimestampAndItIsMoreRecentThan10MinutesThenItIsValid() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);


        boolean validationResult = oAuthSignatureAuthenticationProvider.validateTimestamp(LocalDateTime.now().minusMinutes(5));


        assertThat(validationResult, is(true));
    }

    @Test
    public void whenValidatingOAuthConsumerKeyAndItDiffersThenItIsInvalid() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);


        boolean validationResult = oAuthSignatureAuthenticationProvider.validateConsumerKey("some other key");


        assertThat(validationResult, is(false));
    }

    @Test
    public void whenValidatingOAuthConsumerKeyAndItMatchesThenItIsValid() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);


        boolean validationResult = oAuthSignatureAuthenticationProvider.validateConsumerKey(CONSUMER_KEY);


        assertThat(validationResult, is(true));
    }

    @Test
    public void whenValidatingOAuthNonceAndItHasBeenUsedBeforeThenItIsInvalid() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);

        long oauthNonce = 1000L;
        oAuthSignatureAuthenticationProvider.validateNonce(oauthNonce); // Force the nonce to be saved


        boolean validationResult = oAuthSignatureAuthenticationProvider.validateNonce(oauthNonce);


        assertThat(validationResult, is(false));
    }

    @Test
    public void whenValidatingOAuthNonceAndItHasNeverBeenUsedThenItIsValid() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);


        boolean validationResult = oAuthSignatureAuthenticationProvider.validateNonce(199923232L);


        assertThat(validationResult, is(true));
    }

    @Test
    public void whenValidatingSignatureIfItDiffersFromTheGeneratedHMACSHA1ThenItIsInvalid() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);


        boolean validationResult = oAuthSignatureAuthenticationProvider.validateSignature("Invalid value".getBytes());


        assertThat(validationResult, is(false));
    }

    @Test
    public void whenValidatingSignatureIfItMatchesTheGeneratedHMACSHA1ThenItIsValid() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);

        byte[] validSignature = oAuthSignatureAuthenticationProvider.precalculateSignature(CONSUMER_SECRET);


        boolean validationResult = oAuthSignatureAuthenticationProvider.validateSignature(validSignature);


        assertThat(validationResult, is(true));
    }

    @Test
    public void whenAuthenticatingAndTimestampIsInvalidThenItIsFalse() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);
        OAuthSignature invalidSignature = OAuthSignature.builder()
                .withOAuthTimestamp(LocalDateTime.now()
                .minusDays(45))
                .build();

        boolean validationResult = oAuthSignatureAuthenticationProvider.authenticate(invalidSignature);

        assertThat(validationResult, is(false));
    }

    @Test
    public void whenAuthenticatingAndNonceIsInvalidThenItIsFalse() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);
        OAuthSignature invalidSignature = OAuthSignature.builder()
                .withOAuthTimestamp(LocalDateTime.now())
                .withOAuthNonce(122345)
                .build();
        oAuthSignatureAuthenticationProvider.authenticate(invalidSignature);

        boolean validationResult = oAuthSignatureAuthenticationProvider.authenticate(invalidSignature);

        assertThat(validationResult, is(false));
    }

    @Test
    public void whenAuthenticatingAndConsumerKeyIsInvalidThenItIsFalse() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);
        OAuthSignature invalidSignature = OAuthSignature.builder()
                .withOAuthTimestamp(LocalDateTime.now())
                .withOAuthNonce(122345)
                .withOAuthConsumerKey("Invalid key")
                .build();

        boolean validationResult = oAuthSignatureAuthenticationProvider.authenticate(invalidSignature);

        assertThat(validationResult, is(false));
    }

    @Test
    public void whenAuthenticatingAndSignatureIsInvalidThenItIsFalse() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);
        OAuthSignature invalidSignature = OAuthSignature.builder()
                .withOAuthTimestamp(LocalDateTime.now())
                .withOAuthNonce(122345)
                .withOAuthConsumerKey(CONSUMER_KEY)
                .build();

        boolean validationResult = oAuthSignatureAuthenticationProvider.authenticate(invalidSignature);

        assertThat(validationResult, is(false));
    }

    @Test
    public void whenAuthenticatingAndAllValidationPassThenItIsTrue() {
        OAuthSignatureAuthenticationProvider oAuthSignatureAuthenticationProvider = new OAuthSignatureAuthenticationProvider(CONSUMER_KEY, CONSUMER_SECRET);
        OAuthSignature validSignature = OAuthSignature.builder()
                .withOAuthTimestamp(LocalDateTime.now().minusMinutes(1))
                .withOAuthNonce(122345)
                .withOAuthConsumerKey(CONSUMER_KEY)
                .withOAuthSignature(oAuthSignatureAuthenticationProvider.precalculateSignature(CONSUMER_SECRET))
                .build();

        boolean validationResult = oAuthSignatureAuthenticationProvider.authenticate(validSignature);

        assertThat(validationResult, is(true));
    }


}