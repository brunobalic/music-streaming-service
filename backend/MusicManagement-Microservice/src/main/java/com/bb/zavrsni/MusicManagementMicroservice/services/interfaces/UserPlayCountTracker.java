package com.bb.zavrsni.MusicManagementMicroservice.services.interfaces;

public interface UserPlayCountTracker {

    void setTrackForUser(String trackId, String userId, int trackDuration, Long timeOfRequest);

    void incrementPlayCount(String userId, Long timeOfRequest);

}
