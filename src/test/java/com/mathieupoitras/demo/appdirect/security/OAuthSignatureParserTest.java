package com.mathieupoitras.demo.appdirect.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import oauth.signpost.http.HttpParameters;
import org.junit.Test;

import static com.mathieupoitras.demo.appdirect.security.OAuthSignatureParser.KEY_OAUTH_CONSUMER_KEY;
import static com.mathieupoitras.demo.appdirect.security.OAuthSignatureParser.KEY_OAUTH_NONCE;
import static com.mathieupoitras.demo.appdirect.security.OAuthSignatureParser.KEY_OAUTH_SIGNATURE;
import static com.mathieupoitras.demo.appdirect.security.OAuthSignatureParser.KEY_OAUTH_SIGNATURE_METHOD;
import static com.mathieupoitras.demo.appdirect.security.OAuthSignatureParser.KEY_OAUTH_TIMESTAMP;
import static com.mathieupoitras.demo.appdirect.security.OAuthSignatureParser.KEY_OAUTH_VERSION;
import static com.mathieupoitras.demo.appdirect.security.OAuthSignatureParser.KEY_REALM;
import static com.mathieupoitras.demo.appdirect.security.OAuthSignatureParser.OAUTH_SIGNATURE_METHOD_VALUE_HMAC_SHA1;
import static com.mathieupoitras.demo.appdirect.security.OAuthSignatureParser.OAUTH_VERSION_VALUE_1_0;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Mathieu Poitras on 2016-06-22.
 */
public class OAuthSignatureParserTest {

    @Test
    public void whenRetrievingTheRealmThenFirstValueIsReturned() {
        HttpParameters httpParameters = new HttpParameters();
        String expectedRealmValue = "realmValue";
        httpParameters.put(KEY_REALM, expectedRealmValue);

        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        String actualRealmValue = oAuthSignatureParser.retrieveRealm();


        assertThat(actualRealmValue, is(expectedRealmValue));
    }

    @Test
    public void whenRetrievingTheOAuthConsumerKeyThenValueIsReturned() {
        HttpParameters httpParameters = new HttpParameters();
        String expectedOAuthConsumerKey = "oAuthConsumerKey value";
        httpParameters.put(KEY_OAUTH_CONSUMER_KEY, expectedOAuthConsumerKey);
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        String actualOAuthConsumerKey = oAuthSignatureParser.retrieveOAuthConsumerKey();


        assertThat(actualOAuthConsumerKey, is(expectedOAuthConsumerKey));
    }

    @Test(expected = UnparseableOAuthSignatureException.class)
    public void whenRetrievingTheOAuthConsumerKeyAndItIsEmptyThenAnUnparseableOAuthSignatureExceptionIsThrown() {
        HttpParameters httpParameters = new HttpParameters();
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        oAuthSignatureParser.retrieveOAuthConsumerKey();
    }


    @Test
    public void whenRetrievingTheOAuthSignatureMethodThenValueIsReturned() {
        HttpParameters httpParameters = new HttpParameters();
        String expectedOAuthSignatureMethod = OAUTH_SIGNATURE_METHOD_VALUE_HMAC_SHA1;
        httpParameters.put(KEY_OAUTH_SIGNATURE_METHOD, expectedOAuthSignatureMethod);
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        String actualOAuthSignatureMethod = oAuthSignatureParser.retrieveOAuthSignatureMethod();


        assertThat(actualOAuthSignatureMethod, is(expectedOAuthSignatureMethod));
    }

    @Test(expected = UnparseableOAuthSignatureException.class)
    public void whenRetrievingTheOAuthSignatureMethodAndItIsNotHMACSHA1ThenAnUnparseableOAuthSignatureExceptionIsThrown() {
        HttpParameters httpParameters = new HttpParameters();
        httpParameters.put(KEY_OAUTH_SIGNATURE_METHOD, "Something else");
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        oAuthSignatureParser.retrieveOAuthSignatureMethod();
    }

    @Test(expected = UnparseableOAuthSignatureException.class)
    public void whenRetrievingTheOAuthSignatureMethodAndItIsEmptyThenAnUnparseableOAuthSignatureExceptionIsThrown() {
        HttpParameters httpParameters = new HttpParameters();
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        oAuthSignatureParser.retrieveOAuthSignatureMethod();
    }

    @Test
    public void whenRetrievingTheOAuthSignatureThenValueIsReturned() {
        HttpParameters httpParameters = new HttpParameters();
        String expectedOAuthSignature = "oAuthConsumerKey value";
        httpParameters.put(KEY_OAUTH_SIGNATURE, expectedOAuthSignature);
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        byte[] actualOAuthSignature = oAuthSignatureParser.retrieveOAuthSignature();


        assertThat(actualOAuthSignature, is(expectedOAuthSignature.getBytes()));
    }

    @Test(expected = UnparseableOAuthSignatureException.class)
    public void whenRetrievingTheOAuthSignatureAndItIsEmptyThenAnUnparseableOAuthSignatureExceptionIsThrown() {
        HttpParameters httpParameters = new HttpParameters();
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        oAuthSignatureParser.retrieveOAuthSignature();
    }

    @Test
    public void whenRetrievingTheOAuthVersionThenValueIsReturned() {
        HttpParameters httpParameters = new HttpParameters();
        String expectedOAuthVersion = OAUTH_VERSION_VALUE_1_0;
        httpParameters.put(KEY_OAUTH_VERSION, expectedOAuthVersion);
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        String actualOAuthSignature = oAuthSignatureParser.retrieveOAuthVersion();


        assertThat(actualOAuthSignature, is(expectedOAuthVersion));
    }

    @Test(expected = UnparseableOAuthSignatureException.class)
    public void whenRetrievingTheOAuthVersionAndItIsEmptyThenAnUnparseableOAuthSignatureExceptionIsThrown() {
        HttpParameters httpParameters = new HttpParameters();
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        oAuthSignatureParser.retrieveOAuthSignature();
    }

    @Test
    public void whenRetrievingTheNonceThenValueIsReturned() {
        HttpParameters httpParameters = new HttpParameters();
        Long expectedNonce = 1213l;
        httpParameters.put(KEY_OAUTH_NONCE, expectedNonce.toString());
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        long actualNonce = oAuthSignatureParser.retrieveNonce();


        assertThat(actualNonce, is(expectedNonce));
    }

    @Test(expected = UnparseableOAuthSignatureException.class)
    public void whenRetrievingTheNonceAndItIsNotANumberThenAnUnparseableOAuthSignatureExceptionIsThrown() {
        HttpParameters httpParameters = new HttpParameters();
        httpParameters.put(KEY_OAUTH_NONCE, "invalid");
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        oAuthSignatureParser.retrieveNonce();
    }

    @Test(expected = UnparseableOAuthSignatureException.class)
    public void whenRetrievingTheNonceAndItIsEmptyThenAnUnparseableOAuthSignatureExceptionIsThrown() {
        HttpParameters httpParameters = new HttpParameters();
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        oAuthSignatureParser.retrieveNonce();
    }

    @Test
    public void whenRetrievingTheTimestampThenValueIsReturned() {
        HttpParameters httpParameters = new HttpParameters();
        Long expectedTimestamp = 1213l;
        httpParameters.put(KEY_OAUTH_TIMESTAMP, expectedTimestamp.toString());
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        LocalDateTime actualTimestamp = oAuthSignatureParser.retrieveTimestamp();


        assertThat(actualTimestamp.toEpochSecond(ZoneOffset.UTC), is(expectedTimestamp));
    }

    @Test(expected = UnparseableOAuthSignatureException.class)
    public void whenRetrievingTheTimestampAndItIsNotANumberThenAnUnparseableOAuthSignatureExceptionIsThrown() {
        HttpParameters httpParameters = new HttpParameters();
        httpParameters.put(KEY_OAUTH_TIMESTAMP, "invalid");
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        oAuthSignatureParser.retrieveNonce();
    }

    @Test(expected = UnparseableOAuthSignatureException.class)
    public void whenRetrievingTheTimestampAndItIsEmptyThenAnUnparseableOAuthSignatureExceptionIsThrown() {
        HttpParameters httpParameters = new HttpParameters();
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(httpParameters);


        oAuthSignatureParser.retrieveNonce();
    }
}