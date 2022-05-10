package com.bb.zavrsni.MusicManagementMicroservice.services;

import com.bb.zavrsni.MusicManagementMicroservice.exceptions.UniversalException;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dao.Track;
import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.TrackDto;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.AudioResourceService;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.TrackService;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.UserPlayCountTracker;
import com.bb.zavrsni.MusicManagementMicroservice.utils.RestTemplateInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@EnableScheduling
public class AudioServiceImpl implements AudioResourceService, UserPlayCountTracker {

    @Value("${my_values.microservices.streaming-ms.base-url}")
    private String baseUrl_streamingMS;

    /**
     * Key - user ID
     * Value - PlayCountData
     */
    private ConcurrentMap<String, PlayCountData> userPlayTracker = new ConcurrentHashMap<>();

    /**
     * Key - user ID
     * Value - List of n last played tracks
     */
    private ConcurrentMap<String, BlockingQueue<TrackDto>> userPlayHistory = new ConcurrentHashMap<>();

    private final int PLAY_HISTORY_CAPACITY = 20;
    /**
     * Key - track ID
     * Value - number of plays
     */
    private ConcurrentMap<String, Integer> trackPlayCounter = new ConcurrentHashMap<>();

//    // k - artist id
//    // v -> k - day
//    //      v -> k - track id
//    //           d - play count
//    private ConcurrentMap<String, BlockingQueue<String, Map<String, Integer>>> playStats = new ConcurrentHashMap<>();

    private final TrackService trackService;
    private final RestTemplateInstance rest;
    private final PlayStatsSimulator playStatsSimulator;

    @Autowired
    public AudioServiceImpl(TrackService trackService,
                            RestTemplateInstance restTemplateInstance,
                            PlayStatsSimulator playStatsSimulator) {
        this.trackService = trackService;
        this.rest = restTemplateInstance;
        this.playStatsSimulator = playStatsSimulator;
    }

    //private void setup() {
    @PostConstruct
    private void postConstruct() {
        // load some random initial data

        // add play history to user id: '1'
        String userId = "1";
        userPlayHistory.putIfAbsent(userId, new LinkedBlockingQueue<>(PLAY_HISTORY_CAPACITY));
        BlockingQueue<TrackDto> history = userPlayHistory.get(userId);
        Random random = new Random();
        for (int i = 0; i < PLAY_HISTORY_CAPACITY; i++) {
            //TrackDto randomTrack = trackService.getTrackById(random.nextInt(1760) + 1); // jer ih imam 1-1760
            TrackDto randomTrack = trackService.getTrackById(random.nextInt(159) + 1); // samo od alice keys

            boolean inserted = history.offer(randomTrack);
        }
    }

    //private final double PERCENTAGE = 0.8;
    private static final double PERCENTAGE = 0.3;
    private static final long UPDATE_DB_EVERY = 60 * 1000;

    // za sada ovo necu koristiti
    @Scheduled(fixedDelay = UPDATE_DB_EVERY)
    private void updatePlayCountInDB() {
        //System.out.println("---- hi from scheduled");
        // todo
    }

    @Override
    public byte[] getAudioResource(String trackId, String userId) {

        TrackDto track = trackService.getTrackById(Integer.parseInt(trackId));

        HttpHeaders headers = new HttpHeaders();
        //headers.setBearerAuth();

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        String filename;
        //(track.getFilename() == null || track.getFilename().equals("")) ? filename = "universalTrack" : filename = track.getFilename();
        if (track.getFilename() == null || track.getFilename().equals("")) filename = "universalTrack";
        else filename = track.getFilename();

        ResponseEntity<byte[]> response = rest.getRestTemplate().exchange(
                baseUrl_streamingMS + "/api/media/audio/{filename}",
                HttpMethod.GET,
                requestEntity,
                byte[].class,
                filename
        );


        if (response.getStatusCode() != HttpStatus.OK) {
            throw new UniversalException("Error getting audio resource from streaming ms!");
        }

        Date timeOfRequest = new Date();

        setTrackForUser(track.getId().toString(), userId, track.getDuration_sec(), timeOfRequest.getTime());

        addTrackToPlayHistory(userId, track, timeOfRequest.getTime());

        return response.getBody();

    }

    // todo ovo ne smije biti public
    @Override
    public void setTrackForUser(String trackId, String userId, int trackDuration, Long timeOfRequest) {
        if (userPlayTracker.containsKey(userId)) {
            userPlayTracker.get(userId).updateData(trackId, trackDuration, timeOfRequest);
        } else {
            userPlayTracker.put(userId, new PlayCountData(true, trackId, trackDuration, timeOfRequest));
        }

        trackPlayCounter.putIfAbsent(trackId, 0);

        System.out.println("---- setiran track for user");
    }

    private void addTrackToPlayHistory(String userId, TrackDto trackDto, Long timeOfRequest) {

        userPlayHistory.putIfAbsent(userId, new LinkedBlockingQueue<>(PLAY_HISTORY_CAPACITY));
        BlockingQueue<TrackDto> history = userPlayHistory.get(userId);

        if (history.remainingCapacity() < 1) {
            history.poll();
        }
        boolean added = history.offer(trackDto);
        if (!added) System.out.println("Error: Cannot add to play history!");

    }

    public Object[] getUserPlayHistory(String userId) {
        return userPlayHistory.containsKey(userId) ? userPlayHistory.get(userId).toArray() : null;
    }

    // povecaj brojac za pjesmu koju je user zadnju requestao...
    @Override
    public void incrementPlayCount(String userId, Long timeOfRequest) {

        System.out.println("---- unutar increment play count");

        userPlayTracker.forEach((s, playCountData) -> {
            System.out.println("----");
            System.out.println("key: " + s);
            System.out.println(playCountData.getTrackId());
            System.out.println(playCountData.getTrackDurationSec());
            System.out.println(playCountData.getTrackRequestedAt());
            System.out.println("----");
        });

        if (!userPlayTracker.containsKey(userId)) {
            throw new UniversalException("Error - user did not requested any track!");
        }

        if (!userPlayTracker.get(userId).isTrackerActive()) {
            throw new UniversalException("Error - tracker is disabled!");
        }

        PlayCountData data = userPlayTracker.get(userId);

        long calculatedActualElapsedTime = timeOfRequest - data.getTrackRequestedAt();
        int minimalRequiredTime = (int) (data.getTrackDurationSec() * PERCENTAGE);

        if (calculatedActualElapsedTime >= (minimalRequiredTime * 1000L)) { // *1000 to milisec.

            trackPlayCounter.forEach((k, v) -> {
                System.out.println("++++");
                System.out.println("key:    " + k);
                System.out.println("value:  " + v);
                System.out.println("++++");
            });

            int incrementedValue = trackPlayCounter.get(data.getTrackId()) + 1;

            trackPlayCounter.replace(data.getTrackId(), incrementedValue);

            userPlayTracker.get(userId).resetData();

        } else {
            throw new UniversalException("Error - play count cannot be incremented");
        }

    }

}
