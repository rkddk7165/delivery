package com.sparta.delivery.global.security;

import com.sparta.delivery.domain.user.enums.UserRole;

public record AuthUser(
        String username,
        UserRole role
) {
}