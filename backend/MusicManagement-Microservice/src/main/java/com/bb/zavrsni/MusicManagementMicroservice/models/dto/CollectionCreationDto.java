package com.bb.zavrsni.MusicManagementMicroservice.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionCreationDto {
    private String name;
    private String description;
    private String collectionType;
    private Date releaseDate;
    private Date publishDate;
    private Time publishTime;
    private Integer artistId;
}
