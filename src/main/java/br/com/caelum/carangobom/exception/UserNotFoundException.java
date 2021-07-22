package br.com.caelum.carangobom.exception;

import lombok.Getter;
import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@Getter
@ExceptionMapping(statusCode = HttpStatus.NOT_FOUND, errorCode = "user.not.found.message")
public class UserNotFoundException extends RuntimeException {

    @ExposeAsArg(value = 0, name = "user")
    private final String key;

    public UserNotFoundException(String key) {
        super(key);
        this.key = key;
    }
}
