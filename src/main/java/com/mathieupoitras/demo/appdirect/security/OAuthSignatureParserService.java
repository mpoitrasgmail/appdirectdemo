package com.mathieupoitras.demo.appdirect.security;

import javax.servlet.http.HttpServletRequest;
import oauth.signpost.OAuth;
import oauth.signpost.http.HttpParameters;
import org.springframework.stereotype.Service;

/**
 * Created by Mathieu Poitras on 2016-06-19.
 */
@Service
public class OAuthSignatureParserService {

    /**
     * From OAuth 1.0 specification at: http://oauth.net/core/1.0/#rfc.section.5.4.1
     */
    public OAuthSignature parse(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        HttpParameters params = OAuth.oauthHeaderToParamsMap(authorizationHeader);
        OAuthSignatureParser oAuthSignatureParser = new OAuthSignatureParser(params);

        return OAuthSignature.builder()
                .withRealm(oAuthSignatureParser.retrieveRealm())
                .withOAuthNonce(oAuthSignatureParser.retrieveNonce())
                .withOAuthTimestamp(oAuthSignatureParser.retrieveTimestamp())
                .withOAuthVersion(oAuthSignatureParser.retrieveOAuthVersion())
                .withOAuthConsumerKey(oAuthSignatureParser.retrieveOAuthConsumerKey())
                .withOAuthSignatureMethod(oAuthSignatureParser.retrieveOAuthSignatureMethod())
                .withOAuthSignature(oAuthSignatureParser.retrieveOAuthSignature())
                .build();
    }
}
