package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
public class AlbumDto implements Serializable {
    private final Integer id;
    private final String collection_type;
    private final String name;
    private final String description;
    private final Date official_release_date;
    private final Date publish_date;
    private final Time publish_time;
    private final int number_of_tracks;
    private final int artist_id;
    private final String filename;
    private final int publicVisible;
    private final List<ArtistAlbumDto> artists;
    private final ArtistDto artistObj;
    private final List<TrackDto> tracks;
    private final List<AlbumGenreDto> genres;
}
