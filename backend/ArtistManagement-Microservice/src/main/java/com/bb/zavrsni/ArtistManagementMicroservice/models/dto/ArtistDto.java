package com.bb.zavrsni.ArtistManagementMicroservice.models.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArtistDto implements Serializable {
    private final Integer id;
    private final String auth0id;
    private final String first_name;
    private final String last_name;
    private final String pictureFilename;
    private final String description;
    private final String website_link;
}
