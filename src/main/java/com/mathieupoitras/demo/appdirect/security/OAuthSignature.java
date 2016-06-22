package com.mathieupoitras.demo.appdirect.security;

import java.time.LocalDateTime;

/**
 * Created by Mathieu Poitras on 2016-06-19.
 */
public class OAuthSignature {
    private String realm;
    private long oauthNonce;
    private LocalDateTime oauthTimestamp;
    private String oauthConsumerKey;
    private String oauthSignatureMethod;
    private String oauthVersion;
    private byte[] oauthSignature;

    private OAuthSignature() {
    }

    public String getRealm() {
        return realm;
    }

    public long getOauthNonce() {
        return oauthNonce;
    }

    public LocalDateTime getOauthTimestamp() {
        return oauthTimestamp;
    }

    public String getOauthConsumerKey() {
        return oauthConsumerKey;
    }

    public String getOauthSignatureMethod() {
        return oauthSignatureMethod;
    }

    public String getOauthVersion() {
        return oauthVersion;
    }

    public byte[] getOauthSignature() {
        return oauthSignature;
    }

    public static OAuthSignatureBuilder builder() {
        return new OAuthSignatureBuilder();
    }

    public static class OAuthSignatureBuilder {
        private String realm;
        private long oauthNonce;
        private LocalDateTime oauthTimestamp;
        private String oauthConsumerKey;
        private String oauthSignatureMethod;
        private String oauthVersion;
        private byte[] oauthSignature;

        public OAuthSignature build() {
            OAuthSignature oAuthSignature = new OAuthSignature();
            oAuthSignature.realm = this.realm;
            oAuthSignature.oauthNonce = this.oauthNonce;
            oAuthSignature.oauthTimestamp = this.oauthTimestamp;
            oAuthSignature.oauthConsumerKey = this.oauthConsumerKey;
            oAuthSignature.oauthSignatureMethod = this.oauthSignatureMethod;
            oAuthSignature.oauthVersion = this.oauthVersion;
            oAuthSignature.oauthSignature = this.oauthSignature;
            return oAuthSignature;
        }

        public OAuthSignatureBuilder withRealm(String realm) {
            this.realm = realm;
            return this;
        }

        public OAuthSignatureBuilder withOAuthNonce(long oauthNonce) {
            this.oauthNonce = oauthNonce;
            return this;
        }

        public OAuthSignatureBuilder withOAuthTimestamp(LocalDateTime oauthTimestamp) {
            this.oauthTimestamp = oauthTimestamp;
            return this;
        }

        public OAuthSignatureBuilder withOAuthConsumerKey(String oauthConsumerKey) {
            this.oauthConsumerKey = oauthConsumerKey;
            return this;
        }

        public OAuthSignatureBuilder withOAuthSignatureMethod(String oauthSignatureMethod) {
            this.oauthSignatureMethod = oauthSignatureMethod;
            return this;
        }

        public OAuthSignatureBuilder withOAuthVersion(String oauthVersion) {
            this.oauthVersion = oauthVersion;
            return this;
        }

        public OAuthSignatureBuilder withOAuthSignature(byte[] oauthSignature) {
            this.oauthSignature = oauthSignature;
            return this;
        }
    }
}
