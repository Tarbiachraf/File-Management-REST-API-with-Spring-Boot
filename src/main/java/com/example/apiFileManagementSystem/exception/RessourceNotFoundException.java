package com.example.apiFileManagementSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RessourceNotFoundException extends RuntimeException {
    private String errorCode;
    private String resourceType;
    private String resourceName;
    private Long resourceId;
    private String errorMessage;
    private LocalDateTime timestamp;


    public RessourceNotFoundException(String errorCode, String resourceType, Long resourceId, String detailedMessage) {
        super(detailedMessage);
        this.errorCode = errorCode;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.errorMessage = detailedMessage;
        this.timestamp = LocalDateTime.now();
    }
    public RessourceNotFoundException(String errorCode, String resourceType, String resourceName, String detailedMessage) {
        super(detailedMessage);
        this.errorCode = errorCode;
        this.resourceType = resourceType;
        this.resourceName = resourceName;
        this.errorMessage = detailedMessage;
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

    public String getResourceName() {
        return resourceName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
