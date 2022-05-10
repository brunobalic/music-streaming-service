package com.bb.zavrsni.UserManagementMicroservice.services.interfaces;

import com.bb.zavrsni.UserManagementMicroservice.models.dto.UserCreation;
import com.bb.zavrsni.UserManagementMicroservice.models.dto.UserDto;

public interface UserService {

    UserDto createNewUser(UserCreation user);

    UserDto getUserById(int id);

    UserDto getUserByAuth0Id(String id);

    UserDto getUserByUsername(String username);

    void addToFavouriteAlbums(String userId, String albumId);

    void removeFromFavouriteAlbums(String userId, String albumId);

    void addToFavouriteArtists(String userId, String artistId);

    void removeFromFavouriteArtists(String userId, String artistId);

    void addToFavouriteGenre(String userId, String genreId);

    void removeFromFavouriteGenre(String userId, String genreId);

    void addToFavouritePlaylist(String userId, String playlistId);

    void removeFromFavouritePlaylist(String userId, String playlistId);

    void addToFavouriteTrack(String userId, String trackId);

    void removeFromFavouriteTrack(String userId, String trackId);

    void addRatingToAlbum(String userId, String albumId, int score, String comment);

    void removeRatingFromAlbum(String userId, String albumId);

    void addRatingToTrack(String userId, String trackId, int score, String comment);

    void removeRatingFromTrack(String userId, String trackId);

}
