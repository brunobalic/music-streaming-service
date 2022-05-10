package com.bb.zavrsni.ArtistManagementMicroservice.services.impl;

import com.bb.zavrsni.ArtistManagementMicroservice.Auth0.ManagementApi;
import com.bb.zavrsni.ArtistManagementMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.ArtistManagementMicroservice.models.dto.ArtistCreation;
import com.bb.zavrsni.ArtistManagementMicroservice.models.dto.ArtistDto;
import com.bb.zavrsni.ArtistManagementMicroservice.models.entities.Artist;
import com.bb.zavrsni.ArtistManagementMicroservice.repositories.ArtistRepository;
import com.bb.zavrsni.ArtistManagementMicroservice.services.interfaces.ArtistService;
import com.bb.zavrsni.ArtistManagementMicroservice.utils.mappers.ArtistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final ManagementApi auth0ManagementApi;
    private final ArtistMapper artistMapper;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, ManagementApi managementApi, ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.auth0ManagementApi = managementApi;
        this.artistMapper = artistMapper;
    }

    @Override
    public ArtistDto createArtist(ArtistCreation artistCreation) {
        String tmp1 = "";
        if (artistCreation.getFirst_name().length() < 1) {
            tmp1 += "First name cannot be empty! ";
        }
        if (tmp1.length() > 0) throw new UniversalException(tmp1);

        Map<String, String> result = auth0ManagementApi.createNewArtist(artistCreation.getEmail(), artistCreation.getPassword());
        String auth0Id = result.get("artist_id");
        String errorMsg = result.get("error_message");

        if (auth0Id == null) {
            System.out.println("error creating new user");
            System.out.println(errorMsg);
            //throw new UniversalException("Error - Artist cannot be created!");

            // ovo je preivremeno rjesenje
            String returnMsg = "";
            if (errorMsg.contains("Object didn't pass validation for format email")) {
                System.out.println("tu sam");
                returnMsg += "Invalid email! ";
            }

            if (errorMsg.contains("String is too short (0 chars), minimum 1' on property password")) {
                returnMsg += "Password is too short! ";
            }

            if (errorMsg.contains("PasswordStrengthError: Password is too weak")) {
                returnMsg += "Password is too weak! ";
            }

            if (errorMsg.length() == 0) throw new UniversalException("Error creating user!");
            throw new UniversalException(returnMsg);

        }

        auth0ManagementApi.assignRolesToUser(auth0Id, "ROLE_ARTIST");

        Artist toCreate = new Artist(auth0Id, artistCreation.getFirst_name(), artistCreation.getLast_name());
        Artist created = artistRepository.save(toCreate);
        return artistMapper.artistToArtistDto(created);

    }

    @Override
    public ArtistDto getArtistById(int id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new UniversalException("Artist not found."));
        return artistMapper.artistToArtistDto(artist);
    }

    @Override
    public ArtistDto getArtistByAuth0id(String Auth0id) {
        Artist artist = artistRepository.findByAuth0id(Auth0id)
                .orElseThrow(() -> new UniversalException("Artist not found."));
        return artistMapper.artistToArtistDto(artist);
    }

    @Override
    public ArtistDto updateArtist(ArtistDto artist) {
        Artist toSave = artistMapper.artistDtoToArtist(artist);
        return artistMapper.artistToArtistDto(artistRepository.save(toSave));
    }

    @Override
    public List<ArtistDto> searchArtistsByName(String searchString, int page, int size) {
        if (page < 0) page = 0;
        if (size < 0 || size > 20) size = 5;
        Pageable pageable = PageRequest.of(page, size);
        //return artistRepository.searchArtists(searchString.toLowerCase(), pageable); // important to convert to lower case
        List<Artist> result = artistRepository.searchArtists(searchString.toLowerCase(), pageable);
        return artistMapper.artistToArtistDto(result);
    }

}
