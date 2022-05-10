package com.bb.zavrsni.StreamingMicroservice.services.interfaces;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;

/**
 * Interface for saving and accessing audio resource.
 *
 * // todo - dodat ovaj potpis na sve klase
 * @author Created by Bruno Balic - 2022
 */
public interface AudioResourceService {

    /**
     * Saves audio file.
     *
     * @param file Audio file resource.
     * @param formatName Audio format name.
     * @param trackId ID of the provided audio file.
     * @return Name under which file is saved.
     */
    String saveAudioResource(ByteArrayResource file, String formatName, String trackId);

    /**
     * Returns audio file with requested filename.
     *
     * @param fileName Name of the requested file.
     * @return Audio file resource.
     */
    FileSystemResource getAudioResource(String fileName);

}
