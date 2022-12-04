package com.company.passwordManager.model;

import java.util.Objects;

public class UserPassword {

    private String password;
    private String verifyPassword;


    public UserPassword() {
    }

    public UserPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPassword that = (UserPassword) o;
        return Objects.equals(password, that.password) && Objects.equals(verifyPassword, that.verifyPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, verifyPassword);
    }
}
