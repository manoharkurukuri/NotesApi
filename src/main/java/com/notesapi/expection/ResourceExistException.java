package com.notesapi.expection;

public class ResourceExistException extends RuntimeException {
    public ResourceExistException(String message){
        super(message);
    }
}
