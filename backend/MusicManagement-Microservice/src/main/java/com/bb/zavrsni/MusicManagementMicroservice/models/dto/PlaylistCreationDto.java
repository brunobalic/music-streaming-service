package com.bb.zavrsni.MusicManagementMicroservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistCreationDto {
    private String name;
    private String description;
    private String creator_type;
    private int creator_id;
}
