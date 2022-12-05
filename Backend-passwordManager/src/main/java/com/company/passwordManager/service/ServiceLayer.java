package com.company.passwordManager.service;

import com.company.passwordManager.aes256.PasswordSecurityAES;
import com.company.passwordManager.model.*;
import com.company.passwordManager.repository.PasswordRepository;
import com.company.passwordManager.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

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

  //   6. Find the app by specific id.
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

  //  7. To reset password, find user by email to verify and send them OPT.
  public UserDao findEmailAndReturnUser(String email) throws Exception {

    List<UserDao> foundUser = userRepository.findAll();

    for (UserDao user : foundUser) {
      if (email.equals(user.getEmail())) {

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", sendEmail.getHost());
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.auth", "true");


        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(sendEmail.getSender(), sendEmail.getPassword());
          }
        });

        try
        {
          // MimeMessage object.
          MimeMessage message = new MimeMessage(session);

          // Set From Field: adding senders email to from field.
          message.setFrom(new InternetAddress(sendEmail.getSender()));

          // Set To Field: adding recipient's email to from field.
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

          // Set Subject: subject of the email
          message.setSubject("Your Password Manager One-Time Access Code");

          String messageText = "<h1>Password Manager<h1>";
          messageText += "<h1>Hello <h1>" + "<h1>" + user.getFirstName().toUpperCase() + " " + user.getLastName().toUpperCase() + "</h1><br>";
          messageText += "<p>Your One-Time Password:</p><br>";
          messageText += "<h2>" + String.format("%16d",randomNumb) + "</h2><br>";
          messageText += "<p>Use this code to complete the verification process in the app or website.</p><br>";
          messageText += "<p>Do not share this code with anyone calling you directly. PasswordManager representatives will never reach out to you to verify this code over the phone or SMS.</p>";
          messageText += "_____________________________________________";
          messageText += "<h5>If you have any questions please email us at passwordmanagersecure@outlook.com</h5>";


          // set body of the email.
          message.setContent(messageText, "text/html");

          // Send email.
          Transport.send(message);
          System.out.println("Mail successfully sent");

          UserDao userDao = new UserDao();
          userDao.setEmail(user.getEmail());

          return userDao;
        }
        catch (MessagingException mex) {
          mex.printStackTrace();
        }
      }
    }
    System.out.println("No Email Found!");
    throw new IllegalArgumentException("User with that email does not exist");
  }

  //    7. Now verify the code and return user.
  public UserDao getUserWithCode(@RequestBody ValidationCode validationCode, @PathVariable String email) {

    List<UserDao> foundUser = userRepository.findAll();

    Stack<Password> savedPasswords = new Stack<>();

    UserDao returnUser = new UserDao();

    if (validationCode.getValidationCode() == randomNumb) {

      for (UserDao user : foundUser) {
        if (email.equals(user.getEmail())) {

          returnUser.setUsername(user.getUsername());
          returnUser.setFirstName(user.getFirstName());
          returnUser.setLastName(user.getLastName());
          returnUser.setEmail(user.getEmail());
          returnUser.setPassword(String.valueOf(randomNumb)); // Setting randomNum to validation code so that the same code/user can be used to update the password for same user.

//          Creating a new Random Number so that the previous one can no longer be used;
          Random random = new Random();
          int secureRandom = random.nextInt(9999999);
          randomNumb = secureRandom;
        }


//      Password algo here -> Get that specific user passwords. Update their password_id to the new user. Then delete old passwords that because of Foreign Key restraint.
//                    Then you are able to delete the old user without `fk` errors and then save new users and save corresponding passwords which were updated(id).
        List<Password> allPasswords = passwordRepository.findAll();

        for (Password password : allPasswords) {
          if (password.getUserDao().getId() == user.getId()) {

            Password password1 = new Password();
            password1.setApp(password.getApp());
            password1.setPassword(password.getPassword());
            password1.setUserDao(returnUser);

            savedPasswords.push(password1);

            passwordRepository.delete(password);
          }

        }

        userRepository.delete(user);

        userRepository.save(returnUser); //  Save the new user


        while(!savedPasswords.isEmpty()) {

          passwordRepository.save(savedPasswords.pop());
        }
        System.out.println("Returning User");
        return returnUser;
      }
    }
    throw new IllegalArgumentException("Invalid Code");
  }

  //  9. Update the password.
  public void updatePassword(@RequestBody UserPassword userPassword, @PathVariable int validationCode) {

    UserDao foundUser = userRepository.findByPassword(String.valueOf(validationCode));

    Stack<Password> savedPasswords = new Stack<>();

    UserDao userDao = new UserDao();

    if(!userPassword.getPassword().equals(userPassword.getVerifyPassword()) || userPassword.getPassword() == null || userPassword.getVerifyPassword() == null) {
      throw new IllegalArgumentException("Password do not Match");
    }

    // Verifying code and verifying that both passwords are same.
    if (String.valueOf(validationCode).equals(foundUser.getPassword()) && userPassword.getPassword().equals(userPassword.getVerifyPassword())) {

      userDao.setFirstName(foundUser.getFirstName());
      userDao.setLastName(foundUser.getLastName());
      userDao.setEmail(foundUser.getEmail());
      userDao.setUsername(foundUser.getUsername());
      userDao.setPassword(PasswordSecurityAES.encrypt(userPassword.getVerifyPassword()));

//      Password algo here -> Get that specific user passwords. Update their password_id to the new user. Then delete old passwords that because of Foreign Key restraint.
//                    Then you are able to delete the old user without `fk` errors and then save new users and save corresponding passwords which were updated(id).
      List<Password> allPasswords = passwordRepository.findAll();

      for (Password password : allPasswords) {
        if (password.getUserDao().getId() == foundUser.getId()) {

          Password password1 = new Password();
          password1.setApp(password.getApp());
          password1.setPassword(password.getPassword());
          password1.setUserDao(userDao);

          savedPasswords.push(password1);

          passwordRepository.delete(password);
        }

      }

      userRepository.delete(foundUser);

      userRepository.save(userDao); //  Save the new user


      while (!savedPasswords.isEmpty()) {

        passwordRepository.save(savedPasswords.pop());
      }
      return;
    }
  }

  //    9. Update User Profile -> Any user info to update.

  public void updateUserProfile(@RequestBody UserProfile userProfile, @PathVariable String username) {

    List<UserDao> allUsers = userRepository.findAll();
    UserDao foundUser = userRepository.findByUsername(username);

    Stack<Password> savedPasswords = new Stack<>();

    UserDao user = new UserDao();

    if (username.equals(foundUser.getUsername())) {

      user.setFirstName(userProfile.getFirstName());
      user.setLastName(userProfile.getLastName());
      user.setEmail(userProfile.getEmail());
      user.setUsername(userProfile.getUsername());
      user.setPassword(PasswordSecurityAES.encrypt(userProfile.getPassword()));

//            This is to check if the user input and same saved user, username and email are same then don't throw any error. Otherwise, throw an error.
      for (UserDao ppl : allUsers) {
        if (user.getUsername().equals(foundUser.getUsername()) && user.getEmail().equals(foundUser.getEmail())) {
          System.out.println("Same credentials to User Signed in.");
        } else if(user.getUsername().equals(foundUser.getUsername()) && user.getUsername().equals(ppl.getUsername())) {
          System.out.println("Same Username to User Signed in.");
        } else if(user.getEmail().equals(foundUser.getEmail()) && user.getEmail().equals(ppl.getEmail())) {
          System.out.println("Same Email to User Signed in.");
        } else if (user.getUsername().equals(ppl.getUsername()) && user.getEmail().equals(ppl.getEmail())) {
          System.out.println("Both");
          throw new IllegalArgumentException("Username and Email Already exists");
        } else if (user.getUsername().equals(ppl.getUsername())) {
          System.out.println("Username");
          throw new IllegalArgumentException("Username Already exists");
        } else if (user.getEmail().equals(ppl.getEmail())) {
          System.out.println("Email");
          throw new IllegalArgumentException("Email Already exists");
        }
      }
    }

//      Password algo here -> Get that specific user passwords. Update their password_id to the new user. Then delete old passwords that because of Foreign Key restraint.
//                    Then you are able to delete the old user without `fk` errors and then save new users and save corresponding passwords which were updated(id).
    List<Password> allPasswords = passwordRepository.findAll();

    for(Password password: allPasswords) {
      if(password.getUserDao().getId() == foundUser.getId()) {

        Password password1 = new Password();
        password1.setApp(password.getApp());
        password1.setPassword(password.getPassword());
        password1.setUserDao(user);

        savedPasswords.push(password1);

        passwordRepository.delete(password);
      }

    }

    userRepository.delete(foundUser);

    user = userRepository.save(user); //  Save the new user
    user.setId(user.getId());


    while(!savedPasswords.isEmpty()) {

      passwordRepository.save(savedPasswords.pop());
    }
    if(savedPasswords.isEmpty()) {
      return;
    }

    throw new IllegalArgumentException("Please fill out all the required fields.");
  }

  // 11. Find user profile by their username
  public UserProfile findUserByUsername(@PathVariable String username) {

    UserDao foundUser = userRepository.findByUsername(username);

    if(username.equals(foundUser.getUsername())) {

      UserProfile returnUser = new UserProfile();
      returnUser.setUsername(foundUser.getUsername());
      returnUser.setFirstName(foundUser.getFirstName());
      returnUser.setLastName(foundUser.getLastName());
      returnUser.setEmail(foundUser.getEmail());
      returnUser.setPassword(PasswordSecurityAES.decrypt(foundUser.getPassword()));

      return returnUser;
    }

    throw new IllegalArgumentException("Username not found");
  }

}
