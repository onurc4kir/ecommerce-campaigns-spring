package dev.onurcakir.ecommerce.exception.model;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{
        public NotFoundException(String message) {
            super(message, HttpStatus.NOT_FOUND);
        }
}
