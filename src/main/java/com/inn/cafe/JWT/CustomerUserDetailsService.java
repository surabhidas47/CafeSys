package com.inn.cafe.JWT;

import com.inn.cafe.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    //dao is the user jpa repository
    @Autowired
    UserDao userDao;

    //creating a bean, specify the path bc spring security already has access to user,
    // creating an user of type com.inn.cafe.POJO. specfically here
    // going to use that user variable i the UserDetails method
    private com.inn.cafe.POJO.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername {}",username);

       userDetail = userDao.findByEmailId(username);
       if(!Objects.isNull(userDetail))
           //USer not of POJO type..of springsecurity
           return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
       else
           throw new UsernameNotFoundException("User not found.");
    }

    public  com.inn.cafe.POJO.User getUserDetail(){
        return userDetail;
    }

}
