package com.example.apiFileManagementSystem.models;

import lombok.Data;

@Data
public class CustomErrorResponse {
    private String errorCode;
    private String resourceType;
    private Long resourceId;
    private String detailedMessage;

    public CustomErrorResponse(String errorCode, String resourceType, Long resourceId, String detailedMessage) {
        this.errorCode = errorCode;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.detailedMessage = detailedMessage;
    }
    public CustomErrorResponse(String errorCode, String resourceType,String detailedMessage) {
        this.errorCode = errorCode;
        this.resourceType = resourceType;
        this.detailedMessage = detailedMessage;
    }


    // Getters et setters pour les attributs
}
