package com.bb.zavrsni.WebAppApiGateway.services.interfaces.MusicMS;

public interface AudioResourceService {

    byte[] getAudioResource(String trackId, String userId);

    byte[] getAudioResourceArtistRequest(String trackId, String artistId);

}
