package com.sparta.delivery.domain.user.controller;

import com.sparta.delivery.domain.user.dto.LoginRequest;
import com.sparta.delivery.domain.user.dto.LoginResponse;
import com.sparta.delivery.domain.user.dto.SignupRequest;
import com.sparta.delivery.domain.user.dto.SignupResponse;
import com.sparta.delivery.domain.user.exception.DuplicateNicknameException;
import com.sparta.delivery.domain.user.exception.DuplicateUsernameException;
import com.sparta.delivery.domain.user.repository.UserRepository;
import com.sparta.delivery.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(
            @Valid @RequestBody SignupRequest request
    ){
        SignupResponse response = userService.signup(request);

        return ResponseEntity
                .status(201)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ){
        LoginResponse response = userService.login(request);

        return ResponseEntity
                .status(200)
                .body(response);
    }


}
