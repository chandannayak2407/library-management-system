package com.example.LibraryManagementSystemBackend.exception;

public class APIException extends RuntimeException{
    private static final long serialVersionID = 1L;

    public APIException(){
    }

    public APIException(String message) {
        super(message);
    }
}
