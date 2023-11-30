package com.david.searchcarservice.exceptionhandler;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String e) {
        super(e);
    }
}
