package com.mathieupoitras.demo.appdirect.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Mathieu Poitras on 2016-06-19.
 */
@Service
public class OAuthSignatureAuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(OAuthSignatureAuthenticationProvider.class);

    public static final String HMAC_SHA_1 = "HmacSHA1";

    private final String consumerKey;
    private final byte[] precalculatedSignature;

    private Set<Long> nonceReceived = new HashSet<>();

    @Autowired
    public OAuthSignatureAuthenticationProvider(@Value("${oauth.consumerKey}") String consumerKey,
                                                @Value("${oauth.consumerSecret}") String consumerSecret) {
        this.consumerKey = consumerKey;
        this.precalculatedSignature = precalculateSignature(consumerSecret);
    }

    public boolean authenticate(OAuthSignature oAuthSignature) {
        if (!validateTimestamp(oAuthSignature.getOauthTimestamp())) return false;
        if (!validateNonce(oAuthSignature.getOauthNonce())) return false;
        if (!validateConsumerKey(oAuthSignature.getOauthConsumerKey())) return false;
        if (!validateSignature(oAuthSignature.getOauthSignature())) return false;
        return true;
    }

    boolean validateConsumerKey(String oauthConsumerKey) {
        if(!consumerKey.equals(oauthConsumerKey)){
            return false;
        }
        return true;
    }

    boolean validateTimestamp(LocalDateTime oauthTimestamp) {
        if(LocalDateTime.now().minusMinutes(10).isAfter(oauthTimestamp)){
            return false;
        }
        return true;
    }

    public boolean validateNonce(long oauthNonce) {
        if(nonceReceived.contains(oauthNonce)){
            return false;
        }
        nonceReceived.add(oauthNonce);
        return true;
    }

    public boolean validateSignature(byte[] oauthSignature) {
        if(!Arrays.equals(precalculatedSignature, oauthSignature)){
            return false;
        }
        return true;
    }

    byte[] precalculateSignature(String consumerSecret) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA_1);
            SecretKeySpec consumerSecretKey = new SecretKeySpec(consumerSecret.getBytes(), HMAC_SHA_1);
            mac.init(consumerSecretKey);
            return mac.doFinal();
        } catch (NoSuchAlgorithmException e) {
            logger.warn(HMAC_SHA_1 + " algorithm could not be found",e);
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            logger.warn("The Consumer Secret Key is invalid",e);
            throw new RuntimeException(e);
        }
    }
}
