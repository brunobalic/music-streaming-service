package com.bb.zavrsni.ArtistManagementMicroservice.models.dto;

import lombok.Getter;

@Getter
public class ArtistCreation {
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String description;
    private String website_link;
}
