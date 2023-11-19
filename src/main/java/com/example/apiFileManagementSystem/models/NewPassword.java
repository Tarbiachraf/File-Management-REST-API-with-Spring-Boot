package com.example.apiFileManagementSystem.models;

import lombok.Data;

@Data
public class NewPassword {
    private String oldPassword;
    private String newPassword;
}
