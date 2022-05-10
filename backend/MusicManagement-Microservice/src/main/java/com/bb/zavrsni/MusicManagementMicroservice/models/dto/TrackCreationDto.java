package com.bb.zavrsni.MusicManagementMicroservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackCreationDto {
    private String title;
    private int collectionId;
    private String description;
    private boolean publicTrack;
}
