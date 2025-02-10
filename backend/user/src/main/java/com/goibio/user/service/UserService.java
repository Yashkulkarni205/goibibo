package com.goibio.user.service;

import com.goibio.user.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    String register(UserDTO userDto);

    ResponseEntity<UserDTO> getUser(String email);
}
