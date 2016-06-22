package com.mathieupoitras.demo.appdirect.security;

/**
 * Created by Mathieu Poitras on 2016-06-19.
 */
public class UnparseableOAuthSignatureException extends RuntimeException {
    public UnparseableOAuthSignatureException(String parsingErrorDetails) {
        super(parsingErrorDetails);
    }
}
