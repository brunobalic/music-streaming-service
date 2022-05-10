package com.bb.zavrsni.WebAppApiGateway.models.dto.Artist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistRegistrationFormDto {
    private String email;
    private String password;
    private String password_repeat;
    private String first_name;
    private String last_name;
    private String description;
    private String website_link;

    public boolean isValid() {
        // kod za validaciju forme

        if (!password.equals(password_repeat)) {
            return false;
        }
        // ...
        return true;
    }
}
