package com.mantistech.busreservation.controller.api;

import com.mantistech.busreservation.controller.request.UserSignupRequest;
import com.mantistech.busreservation.dto.model.user.UserDTO;
import com.mantistech.busreservation.dto.response.Response;
import com.mantistech.busreservation.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Response signup(@RequestBody @Valid UserSignupRequest userSignupRequest) {
        UserDTO userDTO = registerUser(userSignupRequest, false);
        Response.ok().setPayload(userDTO);
        System.out.println(Response.ok().getPayload());
        return Response.ok();
    }

    private UserDTO registerUser(UserSignupRequest userSignupRequest, boolean isAdmin) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userSignupRequest.getEmail());
        userDTO.setPassword(userSignupRequest.getPassword());
        userDTO.setFirstName(userSignupRequest.getFirstName());
        userDTO.setLastName(userSignupRequest.getLastName());
        userDTO.setMobileNumber(userSignupRequest.getMobileNumber());
        userDTO.setAdmin(isAdmin);

        return userService.signUp(userDTO);
    }

}
