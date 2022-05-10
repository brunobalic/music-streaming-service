package com.bb.zavrsni.WebAppApiGateway.services.interfaces.ArtistMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Artist.ArtistCreationDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Artist.ArtistDto;

import java.util.List;

/**
 * Interface for communication with Artist Management Micro Service.
 */
public interface ArtistService {

    /**
     * Creates new Artist.
     *
     * @param artistCreationDto Artist creation data
     * @return Newly created Artist object
     */
    ArtistDto createNewArtist(ArtistCreationDto artistCreationDto);

    /**
     * Get Artist by ID.
     *
     * @param artistId Artist ID
     * @return Artist object
     */
    ArtistDto getArtist(String artistId);

    /**
     * Get Artist by Auth0 ID.
     *
     * @param artistAuth0Id Artist Auth0 ID
     * @return Artist object
     */
    ArtistDto getArtistByAuth0Id(String artistAuth0Id);

    /**
     * Search Artists by name.
     *
     * @param query Query string
     * @param page Page
     * @param size Size of page
     * @return List of Artists
     */
    List<ArtistDto> searchArtistsByName(String query, int page, int size);

}
