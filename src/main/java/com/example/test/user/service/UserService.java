package com.example.test.user.service;

import com.example.test.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User login(String principal, String credentials);

    User findByLoginId(String loginId);

    User signup(String principal, String credentials);
}
