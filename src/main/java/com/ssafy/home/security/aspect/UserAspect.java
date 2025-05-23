package com.ssafy.home.security.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ssafy.home.user.dto.User;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class UserAspect {
	
    @Autowired
    PasswordEncoder encoder;

    @Before("execution( * com.ssafy..dao.*.insert(com.ssafy.home.user.dto.User)) && args(user)")
    public void encodeMemberPassword(User user) {
    	user.setPassword(encoder.encode(user.getPassword()));
    }
}
