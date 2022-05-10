package com.bb.zavrsni.ArtistManagementMicroservice.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ARTIST")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "AUTH0_ID", unique = true)
    private String auth0id;

    private String first_name;

    private String last_name;

    private String pictureFilename;

    private String description;

    private String website_link;

    //

    // todo maknit ovo - stavit u mapper
    public Artist(String auth0id, String first_name, String last_name) {
        this.auth0id = auth0id;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
