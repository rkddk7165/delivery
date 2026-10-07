package com.sparta.delivery.domain.user.exception;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {

        super("존재하지 않는 ID입니다.");
    }
}
