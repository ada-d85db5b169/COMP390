package com.yyz.comp390.exception;

public class LoginFailedException extends BaseException {
    public LoginFailedException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
