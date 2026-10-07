package com.sparta.delivery.domain.menu.exception;

public class MenuConflictException extends RuntimeException {
    public MenuConflictException()
    {
        super("이미 등록된 메뉴입니다.");
    }
}
