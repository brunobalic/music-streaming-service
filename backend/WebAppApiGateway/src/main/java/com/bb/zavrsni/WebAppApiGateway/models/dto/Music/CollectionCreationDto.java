package com.bb.zavrsni.WebAppApiGateway.models.dto.Music;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}