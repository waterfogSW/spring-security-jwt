package com.example.test.user.service;

import com.example.test.group.service.GroupService;
import com.example.test.user.entity.User;
import com.example.test.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
@Transactional(readOnly = true)
public class DefaultUserService implements UserService {

    private final static String DEFAULT_GROUP = "USER_GROUP";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupService groupService;

    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, GroupService groupService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.groupService = groupService;
    }

    @Override
    public User login(String principal, String credentials) {
        Assert.isTrue(isNotEmpty(principal), "Principal must be provided");
        Assert.isTrue(isNotEmpty(credentials), "Credentials must be provided");

        User user = userRepository.findByLoginId(principal)
                .orElseThrow(() -> new UsernameNotFoundException("Could not found user for " + principal));
        user.checkPassword(passwordEncoder, credentials);
        return user;
    }

    @Override
    public User findByLoginId(String loginId) {
        Assert.isTrue(isNotEmpty(loginId), "Login Id must be provided");
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot found user for " + loginId));
    }

    @Override
    public User signup(String principal, String credentials) {
        Assert.isTrue(isNotEmpty(principal), "Login id must be provided");
        Assert.isTrue(isNotEmpty(credentials), "Password must be provided");

        final var group = groupService.findByName(DEFAULT_GROUP);
        final var newUser = User.builder()
                .loginId(principal)
                .passwd(passwordEncoder.encode(credentials))
                .group(group)
                .build();

        return userRepository.save(newUser);
    }
}
