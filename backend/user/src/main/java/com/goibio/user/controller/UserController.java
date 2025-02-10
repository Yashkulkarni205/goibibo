package com.goibio.user.controller;

import com.goibio.user.dto.UserDTO;
import com.goibio.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDto)
    {
        String message = userService.register(userDto);
        if(message.equals("success")) return new ResponseEntity<>("Congratulations! " +
                "Your registration is complete. You can now access your account.", HttpStatus.CREATED);
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request, @RequestBody UserDTO userDTO)
    {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // this below line is not necessary setAuthentication also does the same thing.
            request.getSession().setAttribute("USER",userDTO.getEmail());
            return new ResponseEntity<>("Login Successful!",HttpStatus.OK);
        }
        catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid Credentials!",HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("get-user")
    public ResponseEntity<UserDTO> getCurrentUser(HttpServletRequest request)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null) {
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
        String email = authentication.getName();
        userService.getUser(email);
        // or
        // HttpSession session = request.getSession(false); //false means don't create new session if no session
        // if(session!=null) email = session.getAttribute("USER").toString();

        return userService.getUser(email);

    }
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request)
    {
        SecurityContextHolder.clearContext();
        // this below line is not necessary only if you set session while login.
        request.getSession().invalidate();
        return new ResponseEntity<>("Logout Successful!",HttpStatus.OK);
    }

}
