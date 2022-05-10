package com.bb.zavrsni.MusicManagementMicroservice.services;

import com.bb.zavrsni.MusicManagementMicroservice.exceptions.UniversalException;
import lombok.AllArgsConstructor;

//@Getter
@AllArgsConstructor
public class PlayCountData {
    boolean isTrackerActive;
    private String trackId;
    private int trackDurationSec;
    private Long trackRequestedAt;

    public void resetData() {
        this.isTrackerActive = false;
        //this.trackId = null;
        //this.trackDurationSec = 0;
        //this.trackRequestedAt = 0L;
    }

    public void updateData(String trackId, int trackDurationSec, Long trackRequestedAt) {
        this.isTrackerActive = true;
        this.trackId = trackId;
        this.trackDurationSec = trackDurationSec;
        this.trackRequestedAt = trackRequestedAt;
    }

    public boolean isTrackerActive() {
        return isTrackerActive;
    }

    public String getTrackId() {
        if (isTrackerActive) return trackId;
        else throw new UniversalException("Tracker disabled!");
    }

    public int getTrackDurationSec() {
        if (isTrackerActive) return trackDurationSec;
        else throw new UniversalException("Tracker disabled!");
    }

    public Long getTrackRequestedAt() {
        if (isTrackerActive) return trackRequestedAt;
        else throw new UniversalException("Tracker disabled!");
    }
}
