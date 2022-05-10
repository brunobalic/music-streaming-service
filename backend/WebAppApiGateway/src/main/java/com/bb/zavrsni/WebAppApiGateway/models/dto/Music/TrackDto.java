package com.bb.zavrsni.WebAppApiGateway.models.dto.Music;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class TrackDto {
    private Integer id;
    private String title;
    private Timestamp publish_timestamp;
    private Timestamp publish_at;
    private int duration_sec;
    private int artist_id;
    private int album_id;
    private AlbumReducedDto albumData;
    private String description;
    private boolean public_track;
    private int play_count;
    private Timestamp last_played;
    private String filename;
    private int trackPosition;

    private ArtistDto artistObj;
    private List<ArtistTrackDto> artists;
    private List<TrackGenreDto> genres;
}
