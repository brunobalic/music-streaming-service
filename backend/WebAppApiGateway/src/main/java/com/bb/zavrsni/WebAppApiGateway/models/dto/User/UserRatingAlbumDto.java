package com.bb.zavrsni.WebAppApiGateway.models.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRatingAlbumDto {
    private Integer id;
    private int user;
    private int album;
    private int score; // ocjena 1-10
    private String comment;
}
