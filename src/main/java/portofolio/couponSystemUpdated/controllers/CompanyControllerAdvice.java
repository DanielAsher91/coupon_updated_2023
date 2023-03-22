package portofolio.couponSystemUpdated.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = CompanyController.class)
@RestController
public class CompanyControllerAdvice {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorDetails> handle(Exception e) {
        ErrorDetails error = new ErrorDetails("costume value", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
