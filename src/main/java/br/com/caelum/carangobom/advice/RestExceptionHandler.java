package br.com.caelum.carangobom.advice;

import br.com.caelum.carangobom.exception.ApiError;
import br.com.caelum.carangobom.exception.BrandNotFoundException;
import br.com.caelum.carangobom.exception.VehicleNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BrandNotFoundException.class, VehicleNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFound(final Exception ex, WebRequest request) {

        final String error = "Resource not found";

        final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), error);
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
