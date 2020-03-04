package com.mantistech.busreservation.service.impl;

import com.mantistech.busreservation.dto.model.user.UserDTO;
import com.mantistech.busreservation.repository.user.UserRepository;
import com.mantistech.busreservation.service.dao.UserServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceDAO {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO signUp(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDTO updateProfile(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO changePassword(UserDTO userDTO, String newPassword) {
        return null;
    }
}
