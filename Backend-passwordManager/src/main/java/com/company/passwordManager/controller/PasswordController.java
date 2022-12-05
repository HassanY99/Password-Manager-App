package com.company.passwordManager.controller;

import com.company.passwordManager.model.Password;
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
}
