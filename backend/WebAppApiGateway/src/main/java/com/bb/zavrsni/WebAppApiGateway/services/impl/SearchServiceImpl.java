package com.bb.zavrsni.WebAppApiGateway.services.impl;

import com.bb.zavrsni.WebAppApiGateway.exceptions.UniversalException;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Artist.ArtistDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.AlbumDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.PlaylistDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.Music.TrackDto;
import com.bb.zavrsni.WebAppApiGateway.models.dto.SearchResponseDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.ArtistMS.ArtistService;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.AlbumService;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.PlaylistService;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS.TrackService;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final TrackService trackService;
    private final PlaylistService playlistService;

    @Autowired
    public SearchServiceImpl(ArtistService artistService,
                             AlbumService albumService,
                             TrackService trackService,
                             PlaylistService playlistService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.trackService = trackService;
        this.playlistService = playlistService;
    }

    public enum SearchWhat {
        ALBUM("album"),
        ARTIST("artist"),
        PLAYLIST("playlist"),
        TRACK("track");

        private final String value;

        public String getValue() {
            return this.value;
        }

        SearchWhat(String value) {
            this.value = value;
        }
    }

    @Override
    public SearchResponseDto searchAllContent(String query, int page, int size) {
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        searchResponseDto.setArtistDtos(searchArtists(query, page, size));
        searchResponseDto.setAlbumDtos(searchAlbum(query, page, size));
        searchResponseDto.setTrackDtos(searchTrack(query, page, size));
        searchResponseDto.setPlaylistDtos(searchPlaylist(query, page, size));
        return searchResponseDto;
    }

    @Override
    public List<?> searchSpecificContent(String what, String query, int page, int size) {
        List<?> result;
        if (what.equals(SearchWhat.ARTIST.value)) {
            result = searchArtists(query, page, size);
        } else if (what.equals(SearchWhat.ALBUM.value)) {
            result = searchAlbum(query, page, size);
        } else if (what.equals(SearchWhat.TRACK.value)) {
            result = searchTrack(query, page, size);
        } else if (what.equals(SearchWhat.PLAYLIST.value)) {
            result = searchPlaylist(query, page, size);
        } else {
            throw new UniversalException("Content type not matched!");
        }
        return result;
    }

    private List<ArtistDto> searchArtists(String query, int page, int size) {
        return artistService.searchArtistsByName(query, page, size);
    }

    private List<AlbumDto> searchAlbum(String query, int page, int size) {
        return albumService.searchAlbumsByName(query, page, size);
    }

    private List<TrackDto> searchTrack(String query, int page, int size) {
        return trackService.searchTracksByName(query, page, size);
    }

    private List<PlaylistDto> searchPlaylist(String query, int page, int size) {
        return playlistService.searchPlaylistsByName(query, page, size);
    }

}
