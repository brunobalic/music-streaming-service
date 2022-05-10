package com.bb.zavrsni.UserManagementMicroservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreation {
    private String email;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
}
