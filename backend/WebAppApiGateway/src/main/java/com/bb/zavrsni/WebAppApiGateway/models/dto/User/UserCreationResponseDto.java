package com.bb.zavrsni.WebAppApiGateway.models.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationResponseDto {
    private UserDto userDto;
    private String error_msg;
}