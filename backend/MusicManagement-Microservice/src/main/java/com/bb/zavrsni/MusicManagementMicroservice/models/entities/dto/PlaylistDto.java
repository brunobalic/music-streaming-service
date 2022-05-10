package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class PlaylistDto implements Serializable {
    private final Integer id;
    private final String name;
    private final String description;
    private final Date publish_date;
    private final int number_of_tracks;
    private final int creator_id;
    private final String creator_type;
    private final List<TrackPlaylistDto> tracks;
}
