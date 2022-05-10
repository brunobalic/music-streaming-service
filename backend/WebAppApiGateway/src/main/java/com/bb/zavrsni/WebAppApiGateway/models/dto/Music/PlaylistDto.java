package com.bb.zavrsni.WebAppApiGateway.models.dto.Music;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class PlaylistDto {
    private Integer id;
    private String name;
    private String description;
    private Date publish_date;
    private int number_of_tracks;
    private int creator_id; // moze biti artist ili user
    private String creator_type; // na osnovu creator_id dohvati jel creator user ili artist ??
    private List<TrackPlaylistDto> tracks;
}
