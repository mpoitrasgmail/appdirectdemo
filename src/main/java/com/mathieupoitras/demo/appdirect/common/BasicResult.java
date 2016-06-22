package com.mathieupoitras.demo.appdirect.common;

/**
 * Created by Mathieu Poitras on 2016-06-16.
 */
public class BasicResult implements Result {
    private boolean success;
    private String errorCode;
    private String message;

    public BasicResult() {
        this.success = true;
    }

    public BasicResult(String errorCode, String message) {
        this.success = false;
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
