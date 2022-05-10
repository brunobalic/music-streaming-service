package com.bb.zavrsni.MusicManagementMicroservice.models.entities.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GenreDto implements Serializable {
    private final Integer id;
    private final String name;
}
