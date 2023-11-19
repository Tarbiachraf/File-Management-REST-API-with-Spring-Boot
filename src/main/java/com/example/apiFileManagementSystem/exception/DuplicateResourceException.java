package com.example.apiFileManagementSystem.exception;


import java.time.LocalDateTime;

public class DuplicateResourceException extends RuntimeException{
    private String errorCode;
    private String resourceType;
    private String errorMessage;
    private LocalDateTime timestamp;

    public DuplicateResourceException(String errorCode,String resourceType, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.resourceType = resourceType;
        this.errorMessage = errorMessage;
        this.timestamp = LocalDateTime.now();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
