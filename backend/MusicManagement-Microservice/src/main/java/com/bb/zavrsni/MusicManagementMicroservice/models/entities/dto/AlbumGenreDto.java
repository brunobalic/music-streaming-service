package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlbumGenreDto implements Serializable {
    private final Integer id;
    private final int album;
    private final int genre;
}
