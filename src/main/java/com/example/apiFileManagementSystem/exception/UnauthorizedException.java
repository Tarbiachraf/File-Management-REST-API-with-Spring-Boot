package com.example.apiFileManagementSystem.exception;

import java.time.LocalDateTime;

public class UnauthorizedException extends RuntimeException{
    private String errorCode;
    private String resourceType;
    private Long resourceId;
    private String errorMessage;
    private LocalDateTime timestamp;

    public UnauthorizedException(String errorCode,String resourceType, Long resourceId, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.resourceId = resourceId;
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

    public Long getResourceId() {
        return resourceId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
