package portofolio.couponSystemUpdated.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {AdminController.class})
@RestController
public class AdminControllerAdvice {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorDetails> handle(Exception e) {
        ErrorDetails error = new ErrorDetails("Costume value", e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //@RestControllerAdvice(basePackageClasses = {MovieController.class})
//@RestController
//
//public class MovieControllerAdvice {
//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<ErrorDetails> handle(Exception e){
//        ErrorDetails error = new ErrorDetails("Custom Error", e.getMessage());
//        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//    }

}
