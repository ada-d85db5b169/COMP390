package com.yyz.comp390.exception;

public class QueryException extends RuntimeException {
    public QueryException(String message) {
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
