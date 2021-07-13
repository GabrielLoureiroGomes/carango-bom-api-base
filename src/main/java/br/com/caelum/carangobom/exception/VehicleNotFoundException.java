package br.com.caelum.carangobom.exception;

import lombok.Getter;
import me.alidg.errors.annotation.ExceptionMapping;
import me.alidg.errors.annotation.ExposeAsArg;
import org.springframework.http.HttpStatus;

@Getter
@ExceptionMapping(statusCode = HttpStatus.NOT_FOUND, errorCode = "vehicle.not.found.message")
public class VehicleNotFoundException extends Exception {

    @ExposeAsArg(value = 0, name = "vehicle")
    private final String key;

    public VehicleNotFoundException(String key) {
        super(key);
        this.key = key;
    }
}
