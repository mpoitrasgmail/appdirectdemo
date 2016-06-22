package com.mathieupoitras.demo.appdirect.common;

/**
 * Created by Mathieu Poitras on 2016-06-16.
 */
public interface Result {
    boolean isSuccess();
    String getErrorCode();
    String getMessage();
}
