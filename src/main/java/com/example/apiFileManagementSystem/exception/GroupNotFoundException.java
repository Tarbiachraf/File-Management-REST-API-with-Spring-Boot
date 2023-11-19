package com.example.apiFileManagementSystem.exception;


public class GroupNotFoundException extends Exception{
    public GroupNotFoundException(){
        super();
    }
    public GroupNotFoundException(String message){
        super(message);
    }
}
