package com.yyz.comp390.exception;

public class CreateUserException extends BaseException {
    public CreateUserException(String message) {
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
