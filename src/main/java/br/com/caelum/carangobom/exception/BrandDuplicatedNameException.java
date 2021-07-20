package br.com.caelum.carangobom.exception;

import lombok.Getter;
import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@Getter
@ExceptionMapping(statusCode = HttpStatus.BAD_REQUEST, errorCode = "brand.duplicated.name.message")
public class BrandDuplicatedNameException extends RuntimeException {


    @ExposeAsArg(value = 0, name = "brandName")
    private final String key;

    public BrandDuplicatedNameException(String key) {
        super(key);
        this.key = key;
    }

}
