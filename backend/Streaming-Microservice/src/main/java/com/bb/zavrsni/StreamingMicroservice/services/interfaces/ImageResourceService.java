package com.bb.zavrsni.StreamingMicroservice.services.interfaces;

import org.springframework.core.io.ByteArrayResource;

/**
 * Interface for saving and accessing image resource.
 */
public interface ImageResourceService {

    /**
     * Saves image file.
     *
     * @param file Image file resource.
     * @param formatName Image format name.
     * @param type Image of what content type ('album','artist','playlist'...).
     * @param id ID of the content. For example: if the content type is 'album'
     *           then <i>id</i> from that specific album is provided.
     * @return Name under which file is saved.
     */
    String saveImageResource(ByteArrayResource file, String formatName, String type, String id);

    /**
     * Returns image file with requested filename.
     *
     * @param filename File name.
     * @return Image file resource.
     */
    byte[] getImageResource(String filename);

}
