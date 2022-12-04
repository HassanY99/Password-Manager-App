package com.company.passwordManager.model;

import com.company.passwordManager.dto.PasswordDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"IgnoreLazyInitializer", "handler"})
@Table(name = "password")
public class Password {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String app;
  private String password;

  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "password_id", referencedColumnName = "id")
  private UserDao userDao;

  public Password(Integer id, String app, String password, UserDao userDao) {
    this.id = id;
    this.app = app;
    this.password = password;
    this.userDao = userDao;
  }

  public Password() {
  }

  public Password(PasswordDto passwordDto) {

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserDao getUserDao() {
    return userDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Password password1 = (Password) o;
    return Objects.equals(id, password1.id) && Objects.equals(app, password1.app) && Objects.equals(password, password1.password) && Objects.equals(userDao, password1.userDao);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, app, password, userDao);
  }

  @Override
  public String toString() {
    return "Password{" +
      "id=" + id +
      ", app='" + app + '\'' +
      ", password='" + password + '\'' +
      ", userDao=" + userDao +
      '}';
  }


}
