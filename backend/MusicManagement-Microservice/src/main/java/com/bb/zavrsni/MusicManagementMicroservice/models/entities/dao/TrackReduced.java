package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TRACK")
public class TrackReduced {

    @Id
    private Integer id;

    private String title;

    private int duration_sec;

    private int artist_id;

    private int album_id;

    private String filename;

}
