package com.sparta.delivery.domain.menu.entity;


import com.sparta.delivery.domain.menu.enums.MenuStatus;
import com.sparta.delivery.domain.user.entity.User;
import com.sparta.delivery.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access =AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
    private Integer price;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuStatus status = MenuStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Builder
    public Menu(String name, Integer price, String description, User owner) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.owner = owner;
    }

    //메뉴 삭제처리
    public void delete(){
        this.status = MenuStatus.DELETED;
    }

    //메뉴 활성화처리
    public void activate(){
        this.status = MenuStatus.ACTIVE;
    }
}
