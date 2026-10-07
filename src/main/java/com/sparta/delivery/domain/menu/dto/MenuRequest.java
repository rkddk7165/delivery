package com.sparta.delivery.domain.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuRequest {

    @NotBlank(message = "메뉴 이름은 필수입니다")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @Positive(message = "가격은 0원보다 커야 합니다.")
    private Integer price;

    private String description;
}
