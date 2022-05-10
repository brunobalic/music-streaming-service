package com.bb.zavrsni.MusicManagementMicroservice.services;

import com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto.TrackDto;
import com.bb.zavrsni.MusicManagementMicroservice.repositories.TrackRepository;
import com.bb.zavrsni.MusicManagementMicroservice.services.interfaces.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PlayStatsSimulator {

    private final TrackRepository trackRepository;
    private final TrackService trackService;

    private final int HISTORY_DAYS_LONG = 30;

    private long top100GloballyLastUpdated = 0;
    private List<?> top100Globally;

    // k - Artist ID
    // v - blocking queue of type Integer - day index
    //private ConcurrentMap<String, BlockingQueue<PlayStatsData>> stats;
    private ConcurrentMap<String, BlockingQueue<Map<String, Integer>>> stats;

    @Autowired
    public PlayStatsSimulator(TrackRepository trackRepository, TrackService trackService) {
        this.trackRepository = trackRepository;
        this.trackService = trackService;

        this.stats = new ConcurrentHashMap<>();

        // generate random data for all artists
        // ja ih imam 50
        for (int i = 1; i <= 50; i++) {
            String artistId = String.valueOf(i);

            // can hold 30 days of play count history
            this.stats.put(artistId, new LinkedBlockingQueue<>(HISTORY_DAYS_LONG));

            //BlockingQueue<PlayStatsData> daysQueue = this.stats.get(String.valueOf(i));
            BlockingQueue<Map<String, Integer>> daysQueue = this.stats.get(String.valueOf(i));

            List<Integer> artistTracks = trackRepository.findByArtist_id(i);

            System.out.printf("ARTIST: %s\n", i);
            System.out.printf("nb. of tracks: %s\n\n", artistTracks.size());

            Random random = new Random();

            // for each day, add random number of listens to every track
            for (int j = 0; j < HISTORY_DAYS_LONG; j++) {
//                PlayStatsData playStatsData = new PlayStatsData();
//                artistTracks.forEach(t -> playStatsData.getTrackListenCount().put(String.valueOf(t), random.nextInt(1000)));
//                daysQueue.offer(playStatsData);

                Map<String, Integer> playStatsData = new HashMap<>();
                artistTracks.forEach(t -> playStatsData.put(String.valueOf(t), random.nextInt(1000)));
                daysQueue.offer(playStatsData);
            }
        }
    }

    // vraca sve podatke od artist u zadnjih HISTORY_DAYS_LONG dana
    public Object[] getArtistPlayStats(String artistId) {
        return stats.get(artistId).toArray();
    }

    // trebao bi dodat metode za specificnije podatke, dan, track...
    // ali za sada cu samo ovo
    // pa cu na frontendu uzet sta mi triba...

    public List<?> getTop50FromArtist(String artistId) {
        Map<String, Integer> allSongs = new HashMap<>();
        // zbroj svih dana i svih preslusavanja pjesama
        this.stats.get(artistId).forEach(day -> {
            day.forEach((song, count) -> {
                if (allSongs.containsKey(song)) {
                    allSongs.replace(song, allSongs.get(song) + count);
                } else {
                    allSongs.put(song, count);
                }
            });
        });

//        Stream<Map.Entry<String, Integer>> sorted = allSongs
//                .entrySet()
//                .stream()
//                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));

        List<Map.Entry<String, Integer>> result = allSongs
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(50)
                .collect(Collectors.toList());

        List<TrackDto> resultTrackDtos = new ArrayList<>();
        result.forEach(entry -> {
            resultTrackDtos.add(trackService.getTrackById(Integer.parseInt(entry.getKey())));
        });

        //return result; // ovdej je samo trackid i play count
        return resultTrackDtos;

    }

    private List<Map.Entry<String, Integer>> getAllArtistsSongsSortedByPlayCount(String artistId) {
        Map<String, Integer> allSongs = new HashMap<>();
        this.stats.get(artistId).forEach(day -> {
            day.forEach((song, count) -> {
                if (allSongs.containsKey(song)) {
                    allSongs.replace(song, allSongs.get(song) + count);
                } else {
                    allSongs.put(song, count);
                }
            });
        });

        List<Map.Entry<String, Integer>> result = allSongs
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return result;
    }

    // todo
    // ovaj reziltat spremit u varijablu i izmjenjivat svako sat vremena
    public List<?> getGlobalTop100() {

        // update every 1 hour

        if (top100GloballyLastUpdated != 0) {
            if (System.currentTimeMillis() - top100GloballyLastUpdated < 1000 * 3600) {
                System.out.println("will return existing top 100");
                return top100Globally;
            }
        }

        ////////////////////////////////

        Map<String, List<Map.Entry<String, Integer>>> allArtists = new HashMap<>();

        this.stats.forEach((artist, data) -> {
            allArtists.put(artist, getAllArtistsSongsSortedByPlayCount(artist));
        });

        Map<String, Integer> allSongs = new HashMap<>();

        allArtists.forEach((artist, songList) -> {
            songList.forEach(song -> {
                allSongs.put(song.getKey(), song.getValue());
            });
        });

        List<Map.Entry<String, Integer>> result = allSongs
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(100)
                .collect(Collectors.toList());

        List<TrackDto> resultTrackDtos = new ArrayList<>();
        result.forEach(entry -> {
            resultTrackDtos.add(trackService.getTrackById(Integer.parseInt(entry.getKey())));
        });

        this.top100Globally = resultTrackDtos;
        this.top100GloballyLastUpdated = System.currentTimeMillis();

        System.out.println("updated top 100");

        return this.top100Globally;

        //return resultTrackDtos;
    }

}
