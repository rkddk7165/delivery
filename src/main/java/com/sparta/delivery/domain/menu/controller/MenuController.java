package com.sparta.delivery.domain.menu.controller;

import com.sparta.delivery.domain.menu.dto.MenuRequest;
import com.sparta.delivery.domain.menu.dto.MenuResponse;
import com.sparta.delivery.domain.menu.dto.MenuUpdateRequest;
import com.sparta.delivery.domain.menu.entity.Menu;
import com.sparta.delivery.domain.menu.service.MenuService;
import com.sparta.delivery.global.security.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    //메뉴 등록
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody MenuRequest request
    ){
        MenuResponse response = menuService.create(authUser.username(), request);

        return ResponseEntity
                .status(201)
                .body(response);

    }

    //메뉴 목록 조회
    @GetMapping
    public ResponseEntity<List<MenuResponse>> getMenus(){
        List<MenuResponse> menuList = menuService.getMenus();

        return ResponseEntity
                .status(200)
                .body(menuList);
    }

    //메뉴 단건 조회
    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponse> getMenu(
            @PathVariable Long menuId
    ) {
        return ResponseEntity.ok(
                menuService.getMenu(menuId)
        );
    }

    //메뉴 수정
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long menuId,
            @Valid @RequestBody MenuUpdateRequest request){

        MenuResponse response = menuService.update(menuId, authUser.username(), request);

        return ResponseEntity
                .status(200)
                .body(response);

    }
    //메뉴 삭제
    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long menuId
    ){
        menuService.delete(menuId, authUser.username());

        return ResponseEntity
                .status(204)
                .build();
    }
}
