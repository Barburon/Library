package com.cursor.library.controllers;

import com.cursor.library.jwt.JwtUtil;
import com.cursor.library.services.UserService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.io.Serializable;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> createAuthenticationToken(@RequestBody AuthenticationRequest auth) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(),auth.getPassword()));
        final UserDetails userDetails = userService.login(auth.getUsername(), auth.getPassword());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);

    }

    @RequestMapping(value = "/dummy", method = RequestMethod.POST)
    public ResponseEntity<String> createAuthenticationToken(){
        return ResponseEntity.ok("user");

    }

    @Data
    public static class AuthenticationRequest implements Serializable {
        private String username;
        private String password;
    }
}
