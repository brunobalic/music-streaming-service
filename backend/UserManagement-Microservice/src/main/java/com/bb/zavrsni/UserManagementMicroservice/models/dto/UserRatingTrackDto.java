package com.bb.zavrsni.UserManagementMicroservice.models.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRatingTrackDto implements Serializable {
    private final Integer id;
    private final int user;
    private final int track;
    private final int score;
    private final String comment;
}
