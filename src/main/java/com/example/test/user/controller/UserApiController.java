package com.example.test.user.controller;

import com.example.test.jwt.JwtAuthentication;
import com.example.test.jwt.JwtAuthenticationToken;
import com.example.test.user.controller.dto.LoginRequest;
import com.example.test.user.controller.dto.SignupRequest;
import com.example.test.user.controller.dto.UserResponse;
import com.example.test.user.entity.User;
import com.example.test.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserApiController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public UserApiController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("me")
    public UserResponse me(@AuthenticationPrincipal JwtAuthentication authentication) {
        final var foundUser = userService.findByLoginId(authentication.username);
        return new UserResponse(authentication.token, authentication.username, foundUser.getGroup().getName());
    }

    @PostMapping("login")
    public UserResponse login(@RequestBody LoginRequest request) {
        final var authToken = new JwtAuthenticationToken(request.principal(), request.credentials());
        final var resultToken = authenticationManager.authenticate(authToken);
        final var authentication = (JwtAuthentication) resultToken.getPrincipal();
        final var user = (User) resultToken.getDetails();
        return new UserResponse(authentication.token, authentication.username, user.getGroup().getName());
    }

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignupRequest request) {
        userService.signup(request.principal(), request.credentials());
    }
}
