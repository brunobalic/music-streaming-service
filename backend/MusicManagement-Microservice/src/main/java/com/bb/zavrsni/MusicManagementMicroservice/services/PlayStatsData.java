package com.bb.zavrsni.MusicManagementMicroservice.services;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayStatsData {
    // k - track id
    // v - play count
    private Map<String, Integer> trackListenCount = new HashMap<>();
}
