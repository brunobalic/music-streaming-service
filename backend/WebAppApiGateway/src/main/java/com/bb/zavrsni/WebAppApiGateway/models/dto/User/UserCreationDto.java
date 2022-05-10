package com.bb.zavrsni.WebAppApiGateway.models.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {
    private String email;
    private String username;
    private String password;
    private String first_name;
    private String last_name;

    public UserCreationDto(UserRegistrationFormDto userRegistrationFormDto) {
        this.email = userRegistrationFormDto.getEmail();
        this.username = userRegistrationFormDto.getUsername();
        this.password = userRegistrationFormDto.getPassword();
        this.first_name = userRegistrationFormDto.getFirst_name();
        this.last_name = userRegistrationFormDto.getLast_name();
    }
}
