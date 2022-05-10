package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArtistDto implements Serializable {
    private final Integer id;
    private final String first_name;
    private final String last_name;
}
