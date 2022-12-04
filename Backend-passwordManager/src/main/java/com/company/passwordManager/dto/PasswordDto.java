package com.company.passwordManager.dto;

import java.util.Objects;

public class PasswordDto {

  private int id;
  private String app;
  private String password;

  public PasswordDto(int id, String app, String password) {
    this.id = id;
    this.app = app;
    this.password = password;
  }

  public PasswordDto() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PasswordDto that = (PasswordDto) o;
    return id == that.id && Objects.equals(app, that.app) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, app, password);
  }

  @Override
  public String toString() {
    return "PasswordDto{" +
      "id=" + id +
      ", app='" + app + '\'' +
      ", password='" + password + '\'' +
      '}';
  }
}
