package com.booking.system.mapper;

import com.booking.system.dto.UserDTO;
import com.booking.system.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserDTO, User> {

    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);
}
