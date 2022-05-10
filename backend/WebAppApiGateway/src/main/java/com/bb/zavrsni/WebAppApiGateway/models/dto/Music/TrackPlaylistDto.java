package com.bb.zavrsni.WebAppApiGateway.models.dto.Music;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackPlaylistDto {
    private Integer id;
    private int track;
    private int playlist;
    private TrackDto trackObj;
}
