package com.mantistech.busreservation.dto.mapper;

import com.mantistech.busreservation.dto.model.user.UserDTO;
import com.mantistech.busreservation.model.user.User;

public class UserMapper {
    public static UserDTO toUserDTO(User user)
    {
        UserDTO userDTO = new UserDTO();
        return userDTO;
    }
}
