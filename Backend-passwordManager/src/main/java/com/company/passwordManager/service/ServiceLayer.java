package com.company.passwordManager.service;

import com.company.passwordManager.aes256.PasswordSecurityAES;
import com.company.passwordManager.model.Password;
import com.company.passwordManager.model.SendEmail;
import com.company.passwordManager.repository.PasswordRepository;
import com.company.passwordManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ServiceLayer {

  @Autowired
  private PasswordRepository passwordRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SendEmail sendEmail;

  private Random random = new Random();
  private int randomNumb = random.nextInt(999999);

  public ServiceLayer(PasswordRepository passwordRepository) {
    this.passwordRepository = passwordRepository;
  }


  // 1. This will return all the apps associated to that one User.
  public List<Password> findByUsername(String username) {
    List<Password> passwords = passwordRepository.findAll();

    List<Password> passwordList = new ArrayList<>();

    for (Password pass : passwords) {
      if (username.equals(pass.getUserDao().getUsername())) {
        Password foundPassword = new Password();
        foundPassword.setId(pass.getId());
        foundPassword.setApp(pass.getApp());
        foundPassword.setPassword(PasswordSecurityAES.decrypt(pass.getPassword()));
        passwordList.add(foundPassword);
      }
    }
    return passwordList;
  }
}
