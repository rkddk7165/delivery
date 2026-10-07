package com.sparta.delivery.global.exception;

import com.sparta.delivery.domain.menu.exception.MenuNotFoundException;
import com.sparta.delivery.domain.user.exception.DuplicateNicknameException;
import com.sparta.delivery.domain.user.exception.DuplicateUsernameException;
import com.sparta.delivery.domain.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //    409     //
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleDuplicateUsername(
            DuplicateUsernameException e
    ) {
        return ResponseEntity
                .status(409)
                .body(e.getMessage());
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<String> handleDuplicateNickname(
            DuplicateNicknameException e
    ){
        return ResponseEntity
                .status(409)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(
            MethodArgumentNotValidException e
    ) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(message);
    }


    //      404     //
    @ExceptionHandler({
            MenuNotFoundException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> handleNotFound(
            RuntimeException e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", e.getMessage()));
    }

}