package com.bb.zavrsni.UserManagementMicroservice.models.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {
    private final Integer id;
    private final String auth0id;
    private final String username;
    private final String first_name;
    private final String last_name;
    private final List<UserFavouriteAlbumDto> favouriteAlbums;
    private final List<UserFavouriteArtistDto> favouriteArtists;
    private final List<UserFavouriteGenreDto> favouriteGenres;
    private final List<UserFavouritePlaylistDto> favouritePlaylists;
    private final List<UserFavouriteTrackDto> favouriteTracks;
    private final List<UserRatingAlbumDto> ratingAlbums;
    private final List<UserRatingTrackDto> ratingTracks;
}
