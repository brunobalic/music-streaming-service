package com.bb.zavrsni.UserManagementMicroservice.services.impl;

import com.bb.zavrsni.UserManagementMicroservice.Auth0.ManagementApi;
import com.bb.zavrsni.UserManagementMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.UserManagementMicroservice.exceptions.UserCreationException;
import com.bb.zavrsni.UserManagementMicroservice.exceptions.UserNotFoundException;
import com.bb.zavrsni.UserManagementMicroservice.models.dto.UserCreation;
import com.bb.zavrsni.UserManagementMicroservice.models.dto.UserDto;
import com.bb.zavrsni.UserManagementMicroservice.models.entities.*;
import com.bb.zavrsni.UserManagementMicroservice.repositories.*;
import com.bb.zavrsni.UserManagementMicroservice.services.interfaces.UserService;
import com.bb.zavrsni.UserManagementMicroservice.utils.mappers.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FavouriteAlbumRepo favouriteAlbumRepo;
    private final FavouriteArtistRepo favouriteArtistRepo;
    private final FavouriteGenreRepo favouriteGenreRepo;
    private final FavouritePlaylistRepo favouritePlaylistRepo;
    private final FavouriteTrackRepo favouriteTrackRepo;
    private final RatingAlbumRepo ratingAlbumRepo;
    private final RatingTrackRepo ratingTrackRepo;
    private final ManagementApi auth0ManagementApi;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           FavouriteAlbumRepo favouriteAlbumRepo,
                           FavouriteArtistRepo favouriteArtistRepo,
                           FavouriteGenreRepo favouriteGenreRepo,
                           FavouritePlaylistRepo favouritePlaylistRepo,
                           FavouriteTrackRepo favouriteTrackRepo,
                           RatingAlbumRepo ratingAlbumRepo,
                           RatingTrackRepo ratingTrackRepo,
                           ManagementApi auth0ManagementApi,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.favouriteAlbumRepo = favouriteAlbumRepo;
        this.favouriteArtistRepo = favouriteArtistRepo;
        this.favouriteGenreRepo = favouriteGenreRepo;
        this.favouritePlaylistRepo = favouritePlaylistRepo;
        this.favouriteTrackRepo = favouriteTrackRepo;
        this.ratingAlbumRepo = ratingAlbumRepo;
        this.ratingTrackRepo = ratingTrackRepo;
        this.auth0ManagementApi = auth0ManagementApi;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createNewUser(UserCreation user) {
        String tmp1 = "";
        if (user.getFirst_name().length() < 1) {
            tmp1 += "First name cannot be empty! ";
        }
        if (user.getUsername().length() < 3 || user.getUsername().length() > 16) {
            tmp1 += "Username must be between 3-16 characters long! ";
        }

        if (tmp1.length() > 0) throw new UserCreationException(tmp1);

        userRepository.findByUsername(user.getUsername());

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            System.out.println("username already exists in our db");
            throw new UserCreationException(
                    String.format("Sorry, \"%s\" username is already taken.", user.getUsername())
            );
        }

        Map<String, String> result = auth0ManagementApi.createNewUser(user.getEmail(), user.getPassword(), user.getUsername());
        String auth0Id = result.get("user_id");
        String errorMsg = result.get("error_message");

        if (auth0Id == null) {
            System.out.println("error creating new user");
            System.out.println(errorMsg);
            //throw new UserCreationException(errorMsg);
            // ipak su sklopit svoju custom poruku, inace mogu otkriti business logiki...

            // ovo je preivremeno rjesenje
            String returnMsg = "";
            if (errorMsg.contains("Object didn't pass validation for format email")) {
                System.out.println("tu sam");
                returnMsg += "Invalid email! ";
            }

            if (errorMsg.contains("String is too short (0 chars), minimum 1' on property password")) {
                returnMsg += "Password is too short! ";
            }

            if (errorMsg.contains("PasswordStrengthError: Password is too weak")) {
                returnMsg += "Password is too weak! ";
            }

            if (errorMsg.length() == 0) throw new UserCreationException("Error creating user!");
            throw new UserCreationException(returnMsg);
        }

        auth0ManagementApi.assignRolesToUser(auth0Id, "ROLE_REGULAR-USER");

        User createdUser = userRepository.save(new User(auth0Id, user.getUsername(), user.getFirst_name(), user.getLast_name()));
        return userMapper.userToUserDto(createdUser);
    }

    @Override
    public UserDto getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id: %s, not found.", id)));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto getUserByAuth0Id(String id) {
        User user = userRepository.findByAuth0id(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with Auth0 id: %s, not found.", id)));
        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with username: %s, not found.", username)));
        return userMapper.userToUserDto(user);
    }

    //

    @Override
    public void addToFavouriteAlbums(String userId, String albumId) {
        favouriteAlbumRepo
                .findByUserAndAlbum(Integer.parseInt(userId), Integer.parseInt(albumId))
                .ifPresentOrElse(element -> {
                    throw new UniversalException(String.format("Album with ID: %s, is already in favourite list.", albumId));
                }, () -> favouriteAlbumRepo.save(new UserFavouriteAlbum(Integer.parseInt(userId), Integer.parseInt(albumId))));
    }

    @Override
    public void removeFromFavouriteAlbums(String userId, String albumId) {
        favouriteAlbumRepo
                .findByUserAndAlbum(Integer.parseInt(userId), Integer.parseInt(albumId))
                .ifPresent(favouriteAlbumRepo::delete);
    }

    @Override
    public void addToFavouriteArtists(String userId, String artistId) {
        favouriteArtistRepo
                .findByUserAndArtist(Integer.parseInt(userId), Integer.parseInt(artistId))
                .ifPresentOrElse(element -> {
                    throw new UniversalException(String.format("Artist with ID: %s, is already in favourite list.", artistId));
                }, () -> favouriteArtistRepo.save(new UserFavouriteArtist(Integer.parseInt(userId), Integer.parseInt(artistId))));
    }

    @Override
    public void removeFromFavouriteArtists(String userId, String artistId) {
        favouriteArtistRepo
                .findByUserAndArtist(Integer.parseInt(userId), Integer.parseInt(artistId))
                .ifPresent(favouriteArtistRepo::delete);
    }

    @Override
    public void addToFavouriteGenre(String userId, String genreId) {
        favouriteGenreRepo
                .findByUserAndGenre(Integer.parseInt(userId), Integer.parseInt(genreId))
                .ifPresentOrElse(element -> {
                    throw new UniversalException(String.format("Genre with ID: %s, is already in favourite list.", genreId));
                }, () -> favouriteGenreRepo.save(new UserFavouriteGenre(Integer.parseInt(userId), Integer.parseInt(genreId))));
    }

    @Override
    public void removeFromFavouriteGenre(String userId, String genreId) {
        favouriteGenreRepo
                .findByUserAndGenre(Integer.parseInt(userId), Integer.parseInt(genreId))
                .ifPresent(favouriteGenreRepo::delete);
    }

    @Override
    public void addToFavouritePlaylist(String userId, String playlistId) {
        favouritePlaylistRepo
                .findByUserAndPlaylist(Integer.parseInt(userId), Integer.parseInt(playlistId))
                .ifPresentOrElse(element -> {
                    throw new UniversalException(String.format("Playlist with ID: %s, is already in favourite list.", playlistId));
                }, () -> favouritePlaylistRepo.save(new UserFavouritePlaylist(Integer.parseInt(userId), Integer.parseInt(playlistId))));
    }

    @Override
    public void removeFromFavouritePlaylist(String userId, String playlistId) {
        favouritePlaylistRepo
                .findByUserAndPlaylist(Integer.parseInt(userId), Integer.parseInt(playlistId))
                .ifPresent(favouritePlaylistRepo::delete);
    }

    @Override
    public void addToFavouriteTrack(String userId, String trackId) {
        favouriteTrackRepo
                .findByUserAndTrack(Integer.parseInt(userId), Integer.parseInt(trackId))
                .ifPresentOrElse(element -> {
                    throw new UniversalException(String.format("Track with ID: %s, is already in favourite list.", trackId));
                }, () -> favouriteTrackRepo.save(new UserFavouriteTrack(Integer.parseInt(userId), Integer.parseInt(trackId))));
    }

    @Override
    public void removeFromFavouriteTrack(String userId, String trackId) {
        favouriteTrackRepo
                .findByUserAndTrack(Integer.parseInt(userId), Integer.parseInt(trackId))
                .ifPresent(favouriteTrackRepo::delete);
    }

    @Override
    public void addRatingToAlbum(String userId, String albumId, int score, String comment) {
        UserRatingAlbum rating = ratingAlbumRepo
                .findByUserAndAlbum(Integer.parseInt(userId), Integer.parseInt(albumId))
                .orElse(null);

        if (rating == null) {
            ratingAlbumRepo.save(new UserRatingAlbum(Integer.parseInt(userId), Integer.parseInt(albumId), score, comment));
        }
    }

    @Override
    public void removeRatingFromAlbum(String userId, String albumId) {
        UserRatingAlbum rating = ratingAlbumRepo
                .findByUserAndAlbum(Integer.parseInt(userId), Integer.parseInt(albumId))
                .orElse(null);

        if (rating != null) {
            ratingAlbumRepo.delete(rating);
        }
    }

    @Override
    public void addRatingToTrack(String userId, String trackId, int score, String comment) {
        UserRatingTrack rating = ratingTrackRepo
                .findByUserAndTrack(Integer.parseInt(userId), Integer.parseInt(trackId))
                .orElse(null);

        if (rating == null) {
            ratingTrackRepo.save(new UserRatingTrack(Integer.parseInt(userId), Integer.parseInt(trackId), score, comment));
        }
    }

    @Override
    public void removeRatingFromTrack(String userId, String trackId) {
        UserRatingTrack rating = ratingTrackRepo
                .findByUserAndTrack(Integer.parseInt(userId), Integer.parseInt(trackId))
                .orElse(null);

        if (rating != null) {
            ratingTrackRepo.delete(rating);
        }
    }

}
