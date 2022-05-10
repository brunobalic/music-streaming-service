package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao;

import com.bb.zavrsni.MusicManagementMicroservice.models.dto.TrackCreationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private Timestamp publish_timestamp;

    private Timestamp publish_at; // scheduled upload

    private int duration_sec; // ovo se kalkulira prilikom provjere file-a

    private int artist_id;

    //
    @ManyToOne
    @JoinColumn(name = "artist_id", insertable = false, updatable = false) //
    private Artist artistObj;

    //private int album;
    private int album_id;

    @OneToOne
    @JoinColumn(name = "album_id", referencedColumnName = "id", insertable = false, updatable = false)
    private AlbumReduced albumData;

    private String description;

    private boolean public_track = false;

    private int play_count;

    private Timestamp last_played;

    private String filename;

    private int trackPosition;

    @OneToMany(mappedBy = "track", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ArtistTrack> artists;

    @OneToMany(mappedBy = "track", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TrackGenre> genres;

}
