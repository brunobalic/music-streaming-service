package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TrackGenreDto implements Serializable {
    private final Integer id;
    private final int track;
    private final int genre;
}
