package com.company.passwordManager.repository;

import com.company.passwordManager.model.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Integer> {
    UserDao findByUsername(String username);
    UserDao findByPassword(String password);
}
