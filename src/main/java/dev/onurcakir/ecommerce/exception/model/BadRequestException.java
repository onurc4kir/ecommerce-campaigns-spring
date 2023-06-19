package dev.onurcakir.ecommerce.exception.model;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException{
        public BadRequestException(String message) {
            super(message, HttpStatus.BAD_REQUEST);
        }
}
