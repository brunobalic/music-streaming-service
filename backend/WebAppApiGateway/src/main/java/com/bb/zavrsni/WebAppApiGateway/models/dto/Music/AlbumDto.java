package com.bb.zavrsni.WebAppApiGateway.models.dto.Music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
public class AlbumDto {
    private Integer id;
    private String collection_type;
    private String name;
    private String description;
    private Date official_release_date;
    private Date publish_date;
    private Time publish_time;
    private int number_of_tracks;
    private int artist_id;
    private String filename;
    // 0 - hidden, 1 - public, 2 - announced (tracklist hidden)
    private int publicVisible;
    //
    private List<ArtistAlbumDto> artists;
    private ArtistDto artistObj;

    // cini mi se da ne radi za Unirest kada direktno pretvaram u objekt .asObject(AlbumDto.class);
    // radilo bi kada bi ja koristio jackson mapper
    // privremeno rijesenje je da matcham naziv sa json-on
    //@JsonProperty(value = "tracks")
    //private List<TrackDto> trackDtos;
    private List<TrackDto> tracks; // matchan naziv

    private List<AlbumGenreDto> genres;
}
