package com.bb.zavrsni.ArtistManagementMicroservice.services.interfaces;

import com.bb.zavrsni.ArtistManagementMicroservice.models.dto.ArtistCreation;
import com.bb.zavrsni.ArtistManagementMicroservice.models.dto.ArtistDto;

import java.util.List;

public interface ArtistService {

    ArtistDto createArtist(ArtistCreation artist);

    ArtistDto getArtistById(int id);

    ArtistDto getArtistByAuth0id(String Auth0id);

    ArtistDto updateArtist(ArtistDto artist);

    List<ArtistDto> searchArtistsByName(String searchString, int page, int size);

}
