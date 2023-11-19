package com.example.apiFileManagementSystem.models;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class RegisterRequest {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

}
