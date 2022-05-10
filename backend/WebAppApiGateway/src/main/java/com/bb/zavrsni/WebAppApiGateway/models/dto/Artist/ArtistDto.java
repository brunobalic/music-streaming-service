package com.bb.zavrsni.WebAppApiGateway.models.dto.Artist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto {
    private Integer id;
    private String auth0id;
    private String first_name;
    private String last_name;
    private String pictureFilename;
    private String description;
    private String website_link;
}
