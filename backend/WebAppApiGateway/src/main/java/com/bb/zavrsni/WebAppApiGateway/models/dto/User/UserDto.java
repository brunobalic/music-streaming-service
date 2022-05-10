package com.bb.zavrsni.WebAppApiGateway.models.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String auth0id;
    private String username;
    private String first_name;
    private String last_name;

    private List<UserFavouriteAlbumDto> favouriteAlbums;
    private List<UserFavouriteArtistDto> favouriteArtists;
    private List<UserFavouriteGenreDto> favouriteGenres;
    private List<UserFavouritePlaylistDto> favouritePlaylists;
    private List<UserFavouriteTrackDto> favouriteTracks;

    private List<UserRatingAlbumDto> ratingAlbums;
    private List<UserRatingTrackDto> ratingTracks;
}
