package com.goibio.user.service;

import com.goibio.user.dto.UserDTO;
import com.goibio.user.entity.User;
import com.goibio.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
    @Override
    public String register(UserDTO userDto) {
        if(userRepository.findByEmail(userDto.getEmail()).isPresent())
            return "This email is already in use. Please login or try a different email.";
        else if (userRepository.findByEmail(userDto.getPhone()).isPresent())
            return "This phone number is already in use. Please login or try a different one.";
        else {
            User user = userDto.toEntity();
            userRepository.save(user);
            return "success";
        }
    }
    @Override
    public UserDTO getUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.map(User::toDTO).orElse(null);
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByEmail(userDTO.getEmail());
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.save(user);
            return "success";
        }
        return "User not found!";
    }

}
