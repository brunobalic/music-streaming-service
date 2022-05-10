package com.bb.zavrsni.WebAppApiGateway.models.dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationFormDto {
    private String email;
    private String username;
    private String password;
    private String password_repeat;
    private String first_name;
    private String last_name;

    private String error_msg = "";

    public boolean isValid() {
        // kod za validaciju forme

        if (!password.equals(password_repeat)) {
            error_msg = "Passwords do not match!";
            return false;
        }
        // ...
        return true;
    }
}
