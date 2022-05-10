package com.bb.zavrsni.UserManagementMicroservice.utils.mappers;

import com.bb.zavrsni.UserManagementMicroservice.models.dto.UserDto;
import com.bb.zavrsni.UserManagementMicroservice.models.entities.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUserDto(UserDto userDto, @MappingTarget User user);
}
