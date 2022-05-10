package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    /**
     * 0 - not public(hidden)
     * 1 - public visible
     * 2 - album announced, limited data visible(name, cover, release date), individual tracks hidden
     */
    private int publicVisible;

    //

    @OneToMany(mappedBy = "album")
    private List<ArtistAlbum> artists;

    @ManyToOne
    @JoinColumn(name = "artist_id", insertable = false, updatable = false) //
    private Artist artistObj;

    @OneToMany(mappedBy = "album_id")
    private List<Track> tracks;

    @OneToMany(mappedBy = "album")
    private List<AlbumGenre> genres;

    //

    public Album(String name, String description, String collectionType) {
        this.name = name;
        this.description = description;
        this.collection_type = collectionType;
    }

}
