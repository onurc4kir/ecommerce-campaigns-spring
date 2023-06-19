package dev.onurcakir.ecommerce.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException  extends RuntimeException{
    public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    public String message;

    public CustomException(String message,HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
    public CustomException(String message) {
        super(message);
        this.message = message;
    }

}
