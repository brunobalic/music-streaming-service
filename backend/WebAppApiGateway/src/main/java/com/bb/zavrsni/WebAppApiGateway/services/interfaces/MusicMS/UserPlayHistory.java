package com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackDto;

import java.util.List;

public interface UserPlayHistory {

    List<TrackDto> getUserPlayHistory(String userId);

}
