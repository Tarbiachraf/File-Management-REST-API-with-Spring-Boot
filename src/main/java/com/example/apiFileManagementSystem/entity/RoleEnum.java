package com.example.apiFileManagementSystem.entity;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

public enum RoleEnum {
    User("USER"),
    ADMIN("ADMIN");
    private final String displayName;

    RoleEnum(String displayName){
        this.displayName = displayName;
    }
    public String getDisplayName(){
        return this.displayName;
    }


}
