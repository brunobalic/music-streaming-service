package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao;

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
public class ArtistAlbum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int artist;

    private int album;

    public ArtistAlbum(int artist, int album) {
        this.artist = artist;
        this.album = album;
    }

}
