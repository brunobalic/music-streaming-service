package com.bb.zavrsni.MusicManagementMicroservice.services.interfaces;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;

public interface AudioResourceService {

    //String saveAudioResource(ByteArrayResource file, String formatName, String trackId);

    byte[] getAudioResource(String trackId, String userId);

}
