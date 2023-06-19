package dev.onurcakir.ecommerce.exception;

import dev.onurcakir.ecommerce.exception.model.CustomException;
import dev.onurcakir.ecommerce.exception.model.ExceptionResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleExceptions(CustomException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.message);
        ResponseEntity<Object> entity = new ResponseEntity<>(response, exception.status);
        return entity;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionResponse> handleException(ServiceException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(e.getMessage()));
    }


}