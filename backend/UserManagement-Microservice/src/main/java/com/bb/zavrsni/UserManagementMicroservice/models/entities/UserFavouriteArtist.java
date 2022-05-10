package com.bb.zavrsni.UserManagementMicroservice.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserFavouriteArtist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int user;

    private int artist;

    public UserFavouriteArtist(int userId, int artistId) {
        this.user = userId;
        this.artist = artistId;
    }

}
