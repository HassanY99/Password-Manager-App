package com.company.passwordManager.controller;

import com.company.passwordManager.model.Password;
import com.company.passwordManager.model.UserDao;
import com.company.passwordManager.model.ValidationCode;
import com.company.passwordManager.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secure/app")
public class PasswordController {

  @Autowired
  private ServiceLayer serviceLayer;

  @GetMapping("/findById/{username}/safe")
  @ResponseStatus(HttpStatus.OK)
  public List<Password> findAppByUsername(@PathVariable String username) {
    return serviceLayer.findByUsername(username);
  }

  @DeleteMapping("/removeById/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable int id) {
    serviceLayer.deleteById(id);
  }

  @DeleteMapping("/removeUser/{username}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUserByUsername(@PathVariable String username) {
    serviceLayer.deleteUserByUsername(username);
  }

  @PutMapping("/findById/{passwordId}/user/{username}")
  @ResponseStatus(HttpStatus.CREATED)
  public void updateById(@RequestBody Password password, @PathVariable int passwordId, @PathVariable String username) {
    serviceLayer.updateApp(password, passwordId, username);
  }

  @PostMapping("/save/user/{username}")
  @ResponseStatus(HttpStatus.OK)
  public Password saveApp(@RequestBody Password pass, @PathVariable String username) {
    return serviceLayer.saveApp(pass, username);
  }

  @GetMapping("/findBy/{id}/safe")
  @ResponseStatus(HttpStatus.OK)
  public Password findById(@PathVariable int id) {
    return serviceLayer.findTheAppById(id);
  }

  @GetMapping("/findByEmail/{email}/safe")
  @ResponseStatus(HttpStatus.OK)
  public UserDao findByEmail(@PathVariable String email) throws Exception {
    return serviceLayer.findEmailAndReturnUser(email);
  }

  @PostMapping("/getUser/{email}/safe/code")
  @ResponseStatus(HttpStatus.OK)
  public UserDao getUserWithCode(@RequestBody ValidationCode validationCode, @PathVariable String email) {
    return serviceLayer.getUserWithCode(validationCode, email);
  }

}
