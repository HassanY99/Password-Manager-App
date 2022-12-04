package com.company.passwordManager.jwtsecurity.config.service;

import com.company.passwordManager.aes256.PasswordSecurityAES;
import com.company.passwordManager.dto.UserDto;
import com.company.passwordManager.model.UserDao;
import com.company.passwordManager.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public UserDao save(UserDto user) throws Exception {

            List<UserDao> allUsers = userDao.findAll();

            UserDao newUser = new UserDao();
            newUser.setFirstName(StringUtils.capitalize(user.getFirstName()));
            newUser.setLastName(StringUtils.capitalize(user.getLastName()));
            newUser.setEmail(user.getEmail());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(PasswordSecurityAES.encrypt(user.getPassword()));

            for (UserDao ppl : allUsers) {
                if (newUser.getUsername().equals(ppl.getUsername()) && newUser.getEmail().equals(ppl.getEmail())){
                    System.out.println("Both");
                    throw new IllegalArgumentException("Username and Email Already exists");
                } else if(newUser.getEmail().equals(ppl.getEmail())) {
                    System.out.println("Email");
                    throw new IllegalArgumentException("Email Already exists");
                } else if(newUser.getUsername().equals(ppl.getUsername())) {
                    System.out.println("Username");
                    throw new IllegalArgumentException("Username Already exists");
                }
            }

            newUser = userDao.save(newUser);

            return newUser;
    }
}

