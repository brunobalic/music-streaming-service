package com.bb.zavrsni.StreamingMicroservice.services.impl;

import com.bb.zavrsni.StreamingMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.StreamingMicroservice.services.interfaces.AudioResourceService;
import lombok.Getter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class AudioResourceServiceImpl implements AudioResourceService {

    private final String uploadDirectoryAudio = System.getProperty("user.dir") + "/src/main/resources/uploads/music";

    private enum SupportedAudioFormats {
        mp3("mp3"),
        aac("aac");

        @Getter
        private final String value;

        SupportedAudioFormats(String value) {
            this.value = value;
        }
    }

    @PostConstruct
    public void customInit() {
        // custom setup goes here if needed
    }

    private void validateAudioFormat(String formatName) {
        boolean isSupported = Arrays.stream(SupportedAudioFormats.values())
                .anyMatch(supportedAudioFormats -> supportedAudioFormats.getValue().equals(formatName));
        if (!isSupported) {
            String msg = String.format("Provider audio file type is not supported. Provided: '%s', expected: %s", formatName, Arrays.toString(SupportedAudioFormats.values()));
            throw new UniversalException(msg);
        }
    }

    @Override
    public String saveAudioResource(ByteArrayResource file, String fileFormat, String trackId) {
        validateAudioFormat(fileFormat);

        String filename = trackId + "." + fileFormat;
        Path filePath = Paths.get(uploadDirectoryAudio, filename);
        try {
            Files.write(filePath, file.getByteArray());
        } catch (IOException e) {
            String msg = "Error writing file to disk";
            throw new UniversalException(msg, e.getMessage());
        }
        return filename;
    }

    @Override
    public FileSystemResource getAudioResource(String fileName) {
        Path filePath = Paths.get(uploadDirectoryAudio, fileName);
        FileSystemResource file = new FileSystemResource(filePath);
        if (!file.exists()) {
            //String msg = String.format("File with name '%s' does not exist.", fileName);
            //throw new UniversalException(msg, "");

            // !!! samo u dev fazi - ako ne postoji trazena pjesma, vracam default-ni track
            String defaultTrackPath = System.getProperty("user.dir") + "/src/main/resources/uploads/music/" + "instant-crush.mp3";
            return new FileSystemResource(defaultTrackPath);
        }
        return file;
    }

}
