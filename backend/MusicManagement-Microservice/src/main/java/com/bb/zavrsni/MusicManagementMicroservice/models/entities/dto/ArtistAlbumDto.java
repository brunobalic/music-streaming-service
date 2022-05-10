package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArtistAlbumDto implements Serializable {
    private final Integer id;
    private final int artist;
    private final int album;
}
