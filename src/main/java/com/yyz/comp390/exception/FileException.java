package com.yyz.comp390.exception;

public class FileException extends RuntimeException {
    public FileException(String message) {
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
