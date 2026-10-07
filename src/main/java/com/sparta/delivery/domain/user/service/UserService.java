package com.sparta.delivery.domain.user.service;

import com.sparta.delivery.domain.user.dto.LoginRequest;
import com.sparta.delivery.domain.user.dto.LoginResponse;
import com.sparta.delivery.domain.user.dto.SignupRequest;
import com.sparta.delivery.domain.user.dto.SignupResponse;
import com.sparta.delivery.domain.user.entity.User;
import com.sparta.delivery.domain.user.exception.DuplicateNicknameException;
import com.sparta.delivery.domain.user.exception.DuplicateUsernameException;
import com.sparta.delivery.domain.user.exception.InvalidPasswordException;
import com.sparta.delivery.domain.user.exception.InvalidUsernameException;
import com.sparta.delivery.domain.user.repository.UserRepository;
import com.sparta.delivery.global.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponse signup(SignupRequest request){
        //id 중복체크
        //닉네임 중복체크
        validateRequest(request);

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        //User 생성 및 저장
        User user = userRepository.save(User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .role(request.getRole())
                .build());

        //응답dto 반환
        return SignupResponse.from(user);
    }

    @Transactional
    public LoginResponse login(@Valid LoginRequest request) {

        //username 조회
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new InvalidUsernameException()
                );

        //password 조회
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new InvalidPasswordException();
        }

        //access 토큰 발급
        String accessToken = jwtUtil.createAccessToken(
                user.getUsername(),
                user.getRole()
        );

        //access토큰 담아서 반환
        return new LoginResponse(accessToken);


    }

    private void validateRequest(SignupRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new DuplicateUsernameException();
        }

        if(userRepository.existsByNickname(request.getNickname())){
            throw new DuplicateNicknameException();
        }

    }


}

