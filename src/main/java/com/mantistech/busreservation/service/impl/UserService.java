package com.mantistech.busreservation.service.impl;

import com.mantistech.busreservation.dto.mapper.UserMapper;
import com.mantistech.busreservation.dto.model.user.UserDTO;
import com.mantistech.busreservation.enums.EntityType;
import com.mantistech.busreservation.enums.ExceptionType;
import com.mantistech.busreservation.exception.BRSException;
import com.mantistech.busreservation.model.user.Role;
import com.mantistech.busreservation.model.user.User;
import com.mantistech.busreservation.model.user.UserRoles;
import com.mantistech.busreservation.repository.user.RoleRepository;
import com.mantistech.busreservation.repository.user.UserRepository;
import com.mantistech.busreservation.service.dao.BusReservationServiceDAO;
import com.mantistech.busreservation.service.dao.UserServiceDAO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static com.mantistech.busreservation.enums.EntityType.USER;
import static com.mantistech.busreservation.enums.ExceptionType.ENTITY_NOT_FOUND;

@Service
public class UserService implements UserServiceDAO {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private BusReservationServiceDAO busReservationServiceDAO;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO signUp(UserDTO userDTO) {
        Optional<Role> userRole;
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (user == null && !user.isPresent())
        {
            if (userDTO.isAdmin())
            {
                userRole = roleRepository.findByRole(UserRoles.ADMIN.name());

            }else{
                userRole = roleRepository.findByRole(UserRoles.PASSENGER.name());
            }
            if (userRole!=null && userRole.isPresent())
            {
                User user1 = new User();
                user1.setFirstName(userDTO.getFirstName());
                user1.setLastName(userDTO.getLastName());
                user1.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                user1.setEmail(userDTO.getEmail());
                user1.setMobileNumber(userDTO.getMobileNumber());
                user1.setRoles(new HashSet(Arrays.asList(userRole)));
                userRepository.save(user1);
                return UserMapper.toUserDTO(user1);
            }
            throw exception(USER,ExceptionType.ENTITY_EXCEPTION,userDTO.getEmail());
        }
        throw exception(USER,ExceptionType.DUPLICATE_ENTITY,userDTO.getEmail());
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user!=null && user.isPresent())
        {
            return modelMapper.map(user.get(),UserDTO.class);
        }
        throw exception(USER, ENTITY_NOT_FOUND, email);
    }

    @Override
    public UserDTO updateProfile(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (user!=null && user.isPresent()) {
            User userModel = user.get();
            userModel.setFirstName(userDTO.getFirstName());
            userModel.setLastName(userDTO.getLastName());
            userModel.setMobileNumber(userDTO.getMobileNumber());
            return UserMapper.toUserDTO(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDTO.getEmail());
    }

    @Override
    public UserDTO changePassword(UserDTO userDTO, String newPassword) {

        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (user!=null && user.isPresent()) {
            User userModel = user.get();
            userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));
            return UserMapper.toUserDTO(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDTO.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return BRSException.throwException(entityType, exceptionType, args);
    }
}
