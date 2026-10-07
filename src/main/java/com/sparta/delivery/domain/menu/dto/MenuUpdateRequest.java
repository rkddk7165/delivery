package com.sparta.delivery.domain.menu.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuUpdateRequest {

    private String name;

    @Positive(message = "가격은 0원보다 커야 합니다.")
    private Integer price;

    private String description;
}