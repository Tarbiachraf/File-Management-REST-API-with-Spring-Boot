package com.example.apiFileManagementSystem.exception;

public class FileNotFoundException extends Exception{
    public FileNotFoundException(){
        super();
    }
    public FileNotFoundException(String message){
        super(message);
    }
}