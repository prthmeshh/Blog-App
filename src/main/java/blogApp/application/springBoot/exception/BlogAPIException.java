package blogApp.application.springBoot.exception;

import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
public class BlogAPIException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public BlogAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public BlogAPIException(String message,HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

}
