package com.sparta.delivery.domain.menu.service;

import com.sparta.delivery.domain.menu.dto.MenuRequest;
import com.sparta.delivery.domain.menu.dto.MenuResponse;
import com.sparta.delivery.domain.menu.dto.MenuUpdateRequest;
import com.sparta.delivery.domain.menu.entity.Menu;
import com.sparta.delivery.domain.menu.enums.MenuStatus;
import com.sparta.delivery.domain.menu.exception.MenuConflictException;
import com.sparta.delivery.domain.menu.exception.MenuNotFoundException;
import com.sparta.delivery.domain.menu.repository.MenuRepository;
import com.sparta.delivery.domain.user.entity.User;
import com.sparta.delivery.domain.user.exception.UserNotFoundException;
import com.sparta.delivery.domain.user.repository.UserRepository;
import com.sparta.delivery.global.exception.ForbiddenException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    //메뉴 등록
    @Transactional
    public MenuResponse create(String username, MenuRequest request){

        //OWNER 조회
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException()
                );
        //메뉴이름 중복검사
        if(menuRepository.existsByOwnerAndName(owner, request.getName())){
            throw new MenuConflictException();
        }

        //메뉴 생성
        Menu menu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .owner(owner)
                .build();

        Menu savedMenu = menuRepository.save(menu);

        //반환
        return MenuResponse.from(savedMenu);
    }

    public List<MenuResponse> getMenus() {

        List<MenuResponse> menuList = menuRepository.findAllByStatus(MenuStatus.ACTIVE)
                .stream()
                .map(MenuResponse::from)
                .toList();

        return menuList;
    }

    public MenuResponse getMenu(Long menuId) {

        Menu menu = menuRepository
                .findByIdAndStatus(menuId, MenuStatus.ACTIVE)
                .orElseThrow(() ->
                        new MenuNotFoundException()
                );

        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse update(
            Long menuId,
            String username,
            MenuUpdateRequest request
    ) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() ->
                        new MenuNotFoundException()
                );

        if (!menu.getOwner().getUsername().equals(username)) {
            throw new ForbiddenException("본인의 메뉴만 수정할 수 있습니다.");
        }

        if (request.getName() != null) {
            menu.updateName(request.getName());
        }

        if (request.getPrice() != null) {
            menu.updatePrice(request.getPrice());
        }

        if (request.getDescription() != null) {
            menu.updateDescription(request.getDescription());
        }

        return MenuResponse.from(menu);
    }

    @Transactional
    public void delete(Long menuId, String username) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() ->
                        new MenuNotFoundException()
                );

        if (!menu.getOwner().getUsername().equals(username)) {
            throw new ForbiddenException("본인의 메뉴만 삭제할 수 있습니다.");
        }

        menu.delete();


    }
}
