package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.AlbumReduced;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
public class TrackDto implements Serializable {
    private final Integer id;
    private final String title;
    private final Timestamp publish_timestamp;
    private final Timestamp publish_at;
    private final int duration_sec;
    private final int artist_id;
    private final ArtistDto artistObj;
    private final int album_id;
    private final AlbumReduced albumData;
    private final String description;
    private final boolean public_track;
    private final int play_count;
    private final Timestamp last_played;
    private final String filename;
    private final int trackPosition;
    private final List<ArtistTrackDto> artists;
    private final List<TrackGenreDto> genres;
}
