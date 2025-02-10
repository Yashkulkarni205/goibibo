package com.goibio.user.service;

import com.goibio.user.dto.UserDTO;

public interface UserService {
    String register(UserDTO userDto);

    UserDTO getUser(String email);
}
