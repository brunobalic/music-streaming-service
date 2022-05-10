package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrackPlaylist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int track;

    private int playlist;

    public TrackPlaylist(int track, int playlist) {
        this.track = track;
        this.playlist = playlist;
    }

}
