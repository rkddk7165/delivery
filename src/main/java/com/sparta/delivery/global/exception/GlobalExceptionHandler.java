package com.sparta.delivery.global.exception;

import com.sparta.delivery.domain.menu.exception.MenuNotFoundException;
import com.sparta.delivery.domain.order.exception.InvalidOrderStatusException;
import com.sparta.delivery.domain.user.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //      400     //
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException e
    ) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .getFirst()
                .getDefaultMessage();

        return ResponseEntity
                .status(400)
                .body(Map.of("message", message));
    }



    //      401     //
    @ExceptionHandler({
            InvalidUsernameException.class,
            InvalidPasswordException.class
    })
    public ResponseEntity<Map<String, String>> handleInvalidLogin(
            RuntimeException e
    ){
        return ResponseEntity
                .status(401)
                .body(Map.of("message", e.getMessage()));
    }



    //      403     //
    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<Map<String, String>> handleInvalidOrderStatus(
            InvalidOrderStatusException e
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, String>> handleForbidden(
            ForbiddenException e
    ) {
        return ResponseEntity
                .status(403)
                .body(Map.of("message", e.getMessage()));
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



    //    409     //
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateUsername(
            DuplicateUsernameException e
    ) {
        return ResponseEntity
                .status(409)
                .body(Map.of("message", e.getMessage()));

    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateNickname(
            DuplicateNicknameException e
    ){
        return ResponseEntity
                .status(409)
                .body(Map.of("message", e.getMessage()));
    }













}