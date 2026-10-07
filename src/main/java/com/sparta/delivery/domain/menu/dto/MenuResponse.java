package com.sparta.delivery.domain.menu.dto;

import com.sparta.delivery.domain.menu.entity.Menu;
import com.sparta.delivery.domain.menu.enums.MenuStatus;
import com.sparta.delivery.domain.user.dto.SignupResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuResponse {

    private Long id;
    private String name;
    private Integer price;
    private String description;
    private MenuStatus status;

    public static MenuResponse from(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .description(menu.getDescription())
                .status(menu.getStatus())
                .build();

    }
    }

