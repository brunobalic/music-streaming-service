package com.bb.zavrsni.WebAppApiGateway.models.dto.Music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumCreationDto {
    private String collection_type;
    private String name;
    private String description;
    private int number_of_tracks;
    private int artist_id; // artist who uploaded // makniti -> cita se iz tokena
    //
    private List<ArtistAlbumDto> artists;
    private List<TrackDto> trackDtos;
    private List<AlbumGenreDto> genres;
}