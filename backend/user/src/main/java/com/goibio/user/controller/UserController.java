package com.goibio.user.controller;

import com.goibio.user.dto.UserDTO;
import com.goibio.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDto)
    {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        String message = userService.register(userDto);
        if(message.equals("success")) return new ResponseEntity<>("Congratulations! " +
                "Your registration is complete. You can now access your account.", HttpStatus.CREATED);
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(HttpSession session, @RequestBody UserDTO userDTO)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            session.setAttribute("USER",userDTO.getEmail());
            return new ResponseEntity<>("Login Successful!",HttpStatus.OK);
        }
        catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid Credentials!",HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getCurrentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
        String email = authentication.getName();
        UserDTO userDTO = userService.getUser(email);
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session)
    {
        SecurityContextHolder.clearContext();
        session.invalidate();
        return new ResponseEntity<>("Logout Successful!",HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO)
    {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        String message = userService.updateUser(userDTO);
        if(message.equals("success")) return new ResponseEntity<>("User details updated successfully!",HttpStatus.OK);
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
}
