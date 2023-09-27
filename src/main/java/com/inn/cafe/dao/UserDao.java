package com.inn.cafe.dao;

import com.inn.cafe.POJO.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User,Integer> {

    //this is connected to user in POJO
    //method declaration used for querying...
    User findByEmailId(@Param("email") String email);
}
