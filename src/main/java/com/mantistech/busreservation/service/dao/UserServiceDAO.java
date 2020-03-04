package com.mantistech.busreservation.service.dao;

import com.mantistech.busreservation.dto.model.user.UserDTO;

public interface UserServiceDAO {

    public UserDTO signUp(UserDTO userDTO);

    public UserDTO findUserByEmail(String email);

    public UserDTO updateProfile(UserDTO userDTO);

    public UserDTO changePassword(UserDTO userDTO, String newPassword);

}
