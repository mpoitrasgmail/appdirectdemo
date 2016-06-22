package com.mathieupoitras.demo.appdirect.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import oauth.signpost.http.HttpParameters;
import org.springframework.util.StringUtils;

/**
 * Created by Mathieu Poitras on 2016-06-22.
 */
public class OAuthSignatureParser {
    public static final String KEY_REALM = "realm";
    public static final String KEY_OAUTH_TIMESTAMP = "oauth_timestamp";
    public static final String KEY_OAUTH_VERSION = "oauth_version";
    public static final String OAUTH_VERSION_VALUE_1_0 = "1.0";
    public static final String KEY_OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    public static final String KEY_OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    public static final String OAUTH_SIGNATURE_METHOD_VALUE_HMAC_SHA1 = "HMAC-SHA1";
    public static final String KEY_OAUTH_SIGNATURE = "oauth_signature";
    public static final String KEY_OAUTH_NONCE = "oauth_nonce";
    private final HttpParameters params;

    public OAuthSignatureParser(HttpParameters params) {
        this.params = params;
    }

    public String retrieveRealm() {
        return params.getFirst(KEY_REALM);
    }

    public long retrieveNonce() {
        String oauth_nonce = params.getFirst(KEY_OAUTH_NONCE);
        requiredValue(oauth_nonce, "oauth_nonce is missing");
        return parseLong(oauth_nonce, "oauth_nonce cannot be converted to a number");
    }


    public LocalDateTime retrieveTimestamp() {
        String oauth_timestamp = params.getFirst(KEY_OAUTH_TIMESTAMP);
        requiredValue(oauth_timestamp, "oauth_timestamp is missing");
        long secondsSince1970 = parseLong(oauth_timestamp, "oauth_timestamp cannot be converted to a number");

        LocalDateTime timestamp = LocalDateTime.ofEpochSecond(secondsSince1970, 0, ZoneOffset.UTC);
        return timestamp;
    }

    public String retrieveOAuthVersion() {
        String oauth_version = params.getFirst(KEY_OAUTH_VERSION);
        requiredValue(oauth_version, "oauth_version is missing");
        if(!OAUTH_VERSION_VALUE_1_0.equals(oauth_version)){
            throw new UnparseableOAuthSignatureException("oauth_version supported is 1.0 only. Found: " + oauth_version);
        }
        return oauth_version;
    }

    public String retrieveOAuthConsumerKey() {
        String oauth_consumer_key = params.getFirst(KEY_OAUTH_CONSUMER_KEY);
        requiredValue(oauth_consumer_key, "oauth_consumer_key is missing");
        return oauth_consumer_key;
    }

    public String retrieveOAuthSignatureMethod() {
        String oauth_signature_method = params.getFirst(KEY_OAUTH_SIGNATURE_METHOD);
        requiredValue(oauth_signature_method, "oauth_signature_method is missing");
        if(!OAUTH_SIGNATURE_METHOD_VALUE_HMAC_SHA1.equals(oauth_signature_method)){
            throw new UnparseableOAuthSignatureException("oauth_signature_method supported is HMAC-SHA1 only. Found: " + oauth_signature_method);
        }
        return oauth_signature_method;
    }

    public byte[] retrieveOAuthSignature() {
        String oauth_signature = params.getFirst(KEY_OAUTH_SIGNATURE);
        requiredValue(oauth_signature, "oauth_signature is missing");
        return oauth_signature.getBytes();
    }

    void requiredValue(String oauth_nonce, String parsingErrorDetails) {
        if (StringUtils.isEmpty(oauth_nonce)) {
            throw new UnparseableOAuthSignatureException(parsingErrorDetails);
        }
    }

    long parseLong(String longValueAsString, String parsingErrorDetails) {
        try{
            return Long.parseLong(longValueAsString);
        }catch (NumberFormatException nfe){
            throw new UnparseableOAuthSignatureException(parsingErrorDetails);
        }
    }
}
