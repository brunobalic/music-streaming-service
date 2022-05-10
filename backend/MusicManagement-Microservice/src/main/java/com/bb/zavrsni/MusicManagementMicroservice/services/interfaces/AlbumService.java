package com.bb.zavrsni.MusicManagementMicroservice.services.interfaces;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Artist;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Genre;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Track;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.AlbumDto;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

public interface AlbumService {

    AlbumDto createAlbum(ByteArrayResource collectionCreationDtoJson, String artistId, ByteArrayResource artworkFile, String fileExtension);

    AlbumDto getAlbumById(int id);

    void deleteAlbum(int albumId);

    AlbumDto addTracks(int albumId, List<Track> tracks);

    void addArtists(int albumId, List<Artist> artists);

    void addGenres(int albumId, List<Genre> genres);

    void removeGenres(int albumId, List<Genre> genres);

    List<AlbumDto> getAllAlbumsFromArtist(int artistId);

    List<AlbumDto> searchAlbums(String searchString, int page, int size);

}
