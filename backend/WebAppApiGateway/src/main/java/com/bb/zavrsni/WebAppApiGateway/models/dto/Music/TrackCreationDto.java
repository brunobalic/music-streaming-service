package com.bb.zavrsni.WebAppApiGateway.models.dto.Music;

import lombok.Getter;

@Getter
public class TrackCreationDto {

    // todo - izbacit sve nepotrebno !!!

    private String title;
    private int collectionId;
    //private String description; // hocu li ovo imat ?, ma ne treba mi...
    //private boolean publicTrack; // ocu li ovo imat? ili mi je dosta da to imam na kolekcijama

    //private int duration_sec;
    //private int artist_id; // artist who uploaded
    //private int album_id;

    //private List<ArtistTrackDto> artists;
    //private List<TrackGenreDto> genres;
}