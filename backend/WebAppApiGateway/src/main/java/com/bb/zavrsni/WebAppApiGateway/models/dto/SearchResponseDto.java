package com.bb.zavrsni.WebAppApiGateway.models.dto;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Artist.ArtistDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.AlbumDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.PlaylistDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponseDto {
    List<ArtistDto> artistDtos;
    List<AlbumDto> albumDtos;
    List<TrackDto> trackDtos;
    List<PlaylistDto> playlistDtos;
}
