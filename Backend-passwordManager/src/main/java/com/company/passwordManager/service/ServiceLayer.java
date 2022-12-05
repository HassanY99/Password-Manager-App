package com.company.passwordManager.service;

import com.company.passwordManager.aes256.PasswordSecurityAES;
import com.company.passwordManager.model.Password;
import com.company.passwordManager.model.SendEmail;
import com.company.passwordManager.model.UserDao;
import com.company.passwordManager.repository.PasswordRepository;
import com.company.passwordManager.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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


  //    2. DELETE my app and password

  public void deleteById(int id) {
    passwordRepository.deleteById(id);
  }

  //    3. DELETE User by Username and also delete its related passwords
  public void deleteUserByUsername(String username) {

    UserDao foundUser = userRepository.findByUsername(username);

    List<Password> getAllPasswords = passwordRepository.findAll();

    for(Password password: getAllPasswords) {
      if(password.getUserDao().getId() == foundUser.getId()) {
        passwordRepository.delete(password);
      }
    }

    userRepository.delete(foundUser);

  }

  //  4. UPDATE my app and password
  public void updateApp(@RequestBody Password pass, @PathVariable int passwordId, @PathVariable String username) {

    passwordRepository.deleteById(passwordId);


    UserDao userDao = userRepository.findByUsername(username);
    Password password = new Password();

    password.setApp(StringUtils.capitalize(pass.getApp()));
    password.setPassword(PasswordSecurityAES.encrypt(pass.getPassword()));
    password.setUserDao(userDao);
    pass = passwordRepository.save(password);

    pass.setId(password.getId());
  }

  //  5. Save App
  public Password saveApp(@RequestBody Password pass, @PathVariable String username) {

    UserDao userDao = userRepository.findByUsername(username);


    if(username.equals(userDao.getUsername())) {

      Password password = new Password();
      password.setApp(StringUtils.capitalize(pass.getApp())); // Capitalize the first letter here
      password.setPassword(PasswordSecurityAES.encrypt(pass.getPassword()));
      password.setUserDao(userDao);

      return passwordRepository.save(password);
    }
    throw new IllegalArgumentException("Error");
  }

  //    5. Find the app by specific id.
  public Password findTheAppById(int id) {

    Optional<Password> password = passwordRepository.findById(id);

    if (password.isPresent()) {
      Password foundPassword = new Password();
      foundPassword.setApp(password.get().getApp());
      foundPassword.setPassword(PasswordSecurityAES.decrypt(password.get().getPassword()));
      return foundPassword;
    } else {
      return null;
    }
  }

}
