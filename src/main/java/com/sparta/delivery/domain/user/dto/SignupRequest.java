package com.sparta.delivery.domain.user.dto;

import com.sparta.delivery.domain.user.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class SignupRequest {

    @NotBlank(message = "아이디 입력은 필수입니다!!")
    @Size(min = 4, max = 20, message = "아이디는 4~20자로 입력 가능합니다.")
    private String username;

    @NotBlank(message = "비밀번호 입력은 필수입니다!!")
    @Size(min = 4, max = 100, message = "비밀번호는 4~100자 이하로 입력 가능합니다.")
    private String password;

    @NotBlank(message = "닉네임 입력은 필수입니다!!")
    @Size(min = 4, max = 50, message = "닉네임은 4~50자 이하로 입력 가능합니다.")
    private String nickname;

    @NotNull(message = "역할 선택은 필수입니다!!")
    private UserRole role;

}
