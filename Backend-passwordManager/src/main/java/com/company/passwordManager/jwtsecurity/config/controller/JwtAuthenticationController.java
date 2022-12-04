package com.company.passwordManager.jwtsecurity.config.controller;

import com.company.passwordManager.aes256.PasswordSecurityAES;
import com.company.passwordManager.dto.UserDto;
import com.company.passwordManager.jwtsecurity.config.JwtTokenUtil;
import com.company.passwordManager.jwtsecurity.config.model.JwtRequest;
import com.company.passwordManager.jwtsecurity.config.model.JwtResponse;
import com.company.passwordManager.jwtsecurity.config.service.JwtUserDetailsService;
import com.company.passwordManager.model.UserDao;
import com.company.passwordManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Stack;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserDao saveUser(@RequestBody UserDto user) throws Exception {
        return userDetailsService.save(user);
    }

    @RequestMapping(value = "/viewUsers/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findUsers(@PathVariable int id) throws Exception {
        return ResponseEntity.ok(userRepository.findById(id));
    }

    public void authenticate(String username, String password) throws Exception {

        UserDao userUsername = userRepository.findByUsername(username);
        if(userUsername == null) {
            throw new IllegalArgumentException("No User exists credentials");
        }
        String userPassword = userRepository.findByUsername(username).getPassword();

        Stack<String> stacks = new Stack<>();

        try {
            stacks.push(PasswordSecurityAES.encrypt(password));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
                 if(!stacks.pop().equals(userPassword)) {
                    throw new IllegalArgumentException("Password is incorrect");
                }
        }
    }
}

