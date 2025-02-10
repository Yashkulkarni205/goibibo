package com.goibio.user.service;

import com.goibio.user.dto.UserDTO;
import com.goibio.user.entity.User;
import com.goibio.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public String register(UserDTO userDto) {
        if(userRepository.findByEmail(userDto.getEmail()).isPresent())
            return "This email is already in use. Please login or try a different email.";
        else if (userRepository.findByEmail(userDto.getPhone()).isPresent())
            return "This phone number is already in use. Please login or try a different one.";
        else {
            User user = userDto.toEntity();
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user);
            return "success";
        }
    }

    @Override
    public ResponseEntity<UserDTO> getUser(String email) {
        return null;
    }
}
