package com.bb.zavrsni.StreamingMicroservice.services.impl;

import com.bb.zavrsni.StreamingMicroservice.exceptions.ImageFormatSupported;
import com.bb.zavrsni.StreamingMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.StreamingMicroservice.services.interfaces.ImageResourceService;
import lombok.Getter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class ImageResourceServiceImpl implements ImageResourceService {

    private final String uploadDirectoryImage = System.getProperty("user.dir") + "/src/main/resources/uploads/artwork";

    private enum SupportedImageFormats {
        png("png"),
        jpg("jpg");

        @Getter
        private final String value;

        SupportedImageFormats(String value) {
            this.value = value;
        }
    }

    private enum SupportedArtworkTypes {
        ALBUM("album"),
        ARTIST("artist"),
        PLAYLIST("playlist");

        @Getter
        private final String value;

        SupportedArtworkTypes(String value) {
            this.value = value;
        }
    }

    private void validateArtworkType(String type) {
        boolean isSupported = Arrays.stream(SupportedArtworkTypes.values())
                .anyMatch(supportedArtworkTypes -> supportedArtworkTypes.getValue().equals(type));
        if (!isSupported) {
            String msg = String.format("Provider artwork type is not supported. Provided: '%s', expected: %s", type, Arrays.toString(SupportedArtworkTypes.values()));
            throw new UniversalException(msg);
        }
    }

    private void validateImageFormat(String formatName) {
        boolean isSupported = Arrays.stream(SupportedImageFormats.values())
                .anyMatch(supportedImageFormats -> supportedImageFormats.getValue().equals(formatName));
        if (!isSupported) {
            throw new ImageFormatSupported(String.format("Image format is not supported! Provided: '%s', supported: %s", formatName, Arrays.toString(SupportedImageFormats.values())), "");
        }
    }

    @Override
    public String saveImageResource(ByteArrayResource file, String formatName, String type, String id) {
        validateArtworkType(type);
        validateImageFormat(formatName);

        String filename = type + "_" + id + "." + formatName;
        Path fileNameAndPath = Paths.get(uploadDirectoryImage, filename);

        try {
            Files.write(fileNameAndPath, file.getByteArray());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new UniversalException(e.getMessage(), "Error writing image file to disk!");
        }
        return filename;
    }

    @Override
    public byte[] getImageResource(String filename) {
        Path filePath = Paths.get(uploadDirectoryImage, filename);
        FileSystemResource file = new FileSystemResource(filePath);
        if (file.exists()) {
            try {
                return StreamUtils.copyToByteArray(file.getInputStream());
            } catch (IOException e) {
                throw new UniversalException("Error while reading image file!");
            }
        } else {
            throw new UniversalException(String.format("Requested file with name: '%s' does not exist!", filename));
        }
    }

}
