package com.sparta.delivery.domain.payment.exception;

public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException(String message) {

        super(message);
    }
}
