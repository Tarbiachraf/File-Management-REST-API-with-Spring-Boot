package com.example.apiFileManagementSystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserProfile {
    private String email;
    private String lastname;
    private String firstname;
    private String role;

    public UserProfile(String email, String lastName, String firstName, String role) {
        this.email = email;
        this.firstname = firstName;
        this.lastname = lastName;
        this.role = role;
    }
}
