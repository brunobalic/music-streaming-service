package com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS;

import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.AlbumDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.CollectionCreationDto;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface AlbumService {

    AlbumDto createNewAlbum(CollectionCreationDto collectionCreationDto,
                            ByteArrayResource imageFile,
                            String artistId);

    AlbumDto getAlbumById(String albumId);

    List<AlbumDto> getAllPublicAlbumsFromArtist(String artistId);

    List<AlbumDto> getAllAlbumsFromArtist(String artistId);

    void deleteAlbumById(String albumId);

    List<AlbumDto> searchAlbumsByName(String query, int page, int size);

}
