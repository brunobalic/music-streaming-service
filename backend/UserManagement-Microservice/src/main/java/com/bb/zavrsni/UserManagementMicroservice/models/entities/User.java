package com.bb.zavrsni.UserManagementMicroservice.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "AUTH0_ID", unique = true)
    private String auth0id;

    @Column(unique = true)
    private String username;

    private String first_name;

    private String last_name;

    //

    @OneToMany(mappedBy = "user")
    private List<UserFavouriteAlbum> favouriteAlbums;

    @OneToMany(mappedBy = "user")
    private List<UserFavouriteArtist> favouriteArtists;

    @OneToMany(mappedBy = "user")
    private List<UserFavouriteGenre> favouriteGenres;

    @OneToMany(mappedBy = "user")
    private List<UserFavouritePlaylist> favouritePlaylists;

    @OneToMany(mappedBy = "user")
    private List<UserFavouriteTrack> favouriteTracks;

    //

    @OneToMany(mappedBy = "user")
    private List<UserRatingAlbum> ratingAlbums;

    @OneToMany(mappedBy = "user")
    private List<UserRatingTrack> ratingTracks;

    public User(String auth0id, String username, String first_name, String last_name) {
        this.auth0id = auth0id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
    }
}
