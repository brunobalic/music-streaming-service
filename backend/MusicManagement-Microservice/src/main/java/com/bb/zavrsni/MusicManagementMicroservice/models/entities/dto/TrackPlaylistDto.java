package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TrackPlaylistDto implements Serializable {
    private final Integer id;
    private final int track;
    private final int playlist;

    // todo - jel mi treba mapper i za ovo ??

}
