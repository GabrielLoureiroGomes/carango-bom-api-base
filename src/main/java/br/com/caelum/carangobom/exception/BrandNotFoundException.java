package br.com.caelum.carangobom.exception;

import lombok.Getter;
import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@Getter
@ExceptionMapping(statusCode = HttpStatus.NOT_FOUND, errorCode = "brand.not.found.message")
public class BrandNotFoundException extends RuntimeException {

    @ExposeAsArg(value = 0, name = "brand")
    private final String key;

    public BrandNotFoundException(String key) {
        super(key);
        this.key = key;
    }
}
