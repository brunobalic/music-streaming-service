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
public class UserRatingTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int user;

    private int track;

    private int score; // ocjena 1-10

    private String comment;

    public UserRatingTrack(int userId, int trackId, int score, String comment) {
        this.user = userId;
        this.track = trackId;
        this.score = score;
        this.comment = comment;
    }

}
