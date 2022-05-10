package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao;

import com.bb.zavrsni.MusicManagementMicroservice.models.dto.PlaylistCreationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Date publish_date;

    private int number_of_tracks;

    private int creator_id; // moze biti artist ili user

    private String creator_type;

    @OneToMany(mappedBy = "playlist")
    private List<TrackPlaylist> tracks;

}
