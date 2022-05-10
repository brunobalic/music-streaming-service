package com.bb.zavrsni.MusicManagementMicroservice.utils;

import com.bb.zavrsni.MusicManagementMicroservice.exceptions.FileNotValidException;
import com.bb.zavrsni.MusicManagementMicroservice.exceptions.UniversalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class AudioFileValidator {

    // todo - pre destroy - delete all tmp files ??

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String tmpUploadDirectoryPath = System.getProperty("user.dir") + "/src/main/resources/uploads/tmp";

    private enum SupportedAudioFormats {
//        mp3("mp3"),
//        aac("aac"); // ukidam support za aac... nemam to jos hendlano za browser
        mp3("mp3");

        @Getter
        private final String value;

        SupportedAudioFormats(String value) {
            this.value = value;
        }
    }

    public static class StreamHelper implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public String output = "";

        public StreamHelper(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            output = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining());
        }
    }

    private FileMetadata validateAudioFileAndExtractMetadata(ByteArrayResource file) throws IOException, InterruptedException {
        UUID uuid = UUID.randomUUID();

        String tmpFileName = uuid.toString();
        Path tmpFilePath = Paths.get(tmpUploadDirectoryPath, tmpFileName);

        try {
            Files.write(tmpFilePath, file.getByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String command = String.format("ffprobe -v quiet -print_format json -show_format -show_streams %s", tmpFilePath);

        Process process = Runtime.getRuntime().exec(command);

        StreamHelper streamHelper = new StreamHelper(process.getInputStream(), System.out::println);

        Executors.newSingleThreadExecutor().submit(streamHelper);

        int exitCode = process.waitFor();

        assert exitCode == 0;

        Files.delete(tmpFilePath);

        String formatName;
        double duration;
        int probeScore;

        ObjectNode objectNode = objectMapper.readValue(streamHelper.output, ObjectNode.class);

        if (objectNode.has("format") &&
                objectNode.get("format").has("format_name") &&
                objectNode.get("format").has("duration") &&
                objectNode.get("format").has("probe_score")) {

            formatName = objectNode.get("format").get("format_name").textValue();
            duration = objectNode.get("format").get("duration").asDouble();
            probeScore = objectNode.get("format").get("probe_score").asInt();
        } else {
            throw new FileNotValidException("Failed to validate provided audio file!");
        }

        validateAudioFormat(formatName);

        if (probeScore < 51) {
            throw new FileNotValidException("Provided audio file is not valid. It is low quality or potentially corrupt!");
        }

        return new FileMetadata(formatName, duration);
    }

    private void validateAudioFormat(String formatName) {
        boolean isSupported = Arrays.stream(SupportedAudioFormats.values())
                .anyMatch(supportedAudioFormats -> supportedAudioFormats.getValue().equals(formatName));
        if (!isSupported) {
            throw new UniversalException(String.format("Audio type not supported! Provided: %s , supported %s", formatName, Arrays.toString(SupportedAudioFormats.values())));
        }
    }

    public FileMetadata validateAudioFile(ByteArrayResource file, String fileExtension) {
        validateAudioFormat(fileExtension); // ovo je validacija file extensiona koji je zapisan uz ime file (12.mp3)
        try {
            return validateAudioFileAndExtractMetadata(file);
        } catch (IOException | InterruptedException e) {
            throw new UniversalException(e.getMessage());
        }
    }

}
