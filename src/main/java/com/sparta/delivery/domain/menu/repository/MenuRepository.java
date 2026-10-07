package com.sparta.delivery.domain.menu.repository;

import com.sparta.delivery.domain.menu.entity.Menu;
import com.sparta.delivery.domain.menu.enums.MenuStatus;
import com.sparta.delivery.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    boolean existsByOwnerAndName(User owner, String name);

    List<Menu> findAllByStatus(MenuStatus status);

    Optional<Menu> findByIdAndStatus(Long menuId, MenuStatus menuStatus);
}
