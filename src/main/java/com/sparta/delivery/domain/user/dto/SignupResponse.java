package com.sparta.delivery.domain.user.dto;

import com.sparta.delivery.domain.user.entity.User;
import com.sparta.delivery.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignupResponse {

    private String username;
    private String nickname;
    private UserRole role;

    public static SignupResponse from(User user) {
        return SignupResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .role(user.getRole())
                .build();
    }
}