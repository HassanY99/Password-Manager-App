package com.company.passwordManager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import sun.security.util.Password;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"IgnoreLazyInitializer", "handler"})
@Table(name = "users_info")
public class UserDao {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  @Column
  private String firstName;
  @Column
  private String lastName;
  @Column(unique = true)
  private String email;
  @Column(unique = true)
  private String username;
  @Column
  @JsonIgnore
  private String password;

  @OneToMany(mappedBy = "password", fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE })
  private Set<Password> passwords = new HashSet<>();

  public UserDao(int id, String firstName, String lastName, String username, String password, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public UserDao() {
  }

  public UserDao(String email) {
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Password> getPasswords() {
    return passwords;
  }

  public void setPasswords(Set<Password> passwords) {
    this.passwords = passwords;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDao userDao = (UserDao) o;
    return id == userDao.id && Objects.equals(firstName, userDao.firstName) && Objects.equals(lastName, userDao.lastName) && Objects.equals(email, userDao.email) && Objects.equals(username, userDao.username) && Objects.equals(password, userDao.password) && Objects.equals(passwords, userDao.passwords);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, email, username, password, passwords);
  }

  @Override
  public String toString() {
    return "UserDao{" +
      "id=" + id +
      ", firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", email='" + email + '\'' +
      ", username='" + username + '\'' +
      ", password='" + password + '\'' +
      ", passwords=" + passwords +
      '}';
  }
}
