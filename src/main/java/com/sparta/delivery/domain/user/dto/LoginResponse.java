package com.sparta.delivery.domain.user.dto;

import com.sparta.delivery.domain.user.entity.User;
import com.sparta.delivery.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String accessToken;
}