package com.bb.zavrsni.UserManagementMicroservice.models.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserFavouriteAlbumDto implements Serializable {
    private final Integer id;
    private final int user;
    private final int album;
}
