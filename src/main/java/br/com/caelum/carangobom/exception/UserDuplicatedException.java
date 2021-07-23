package br.com.caelum.carangobom.exception;

import lombok.Getter;
import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@Getter
@ExceptionMapping(statusCode = HttpStatus.BAD_REQUEST, errorCode = "user.duplicated.message")
public class UserDuplicatedException extends RuntimeException {


    @ExposeAsArg(value = 0, name = "user")
    private final String key;

    public UserDuplicatedException(String key) {
        super(key);
        this.key = key;
    }
}