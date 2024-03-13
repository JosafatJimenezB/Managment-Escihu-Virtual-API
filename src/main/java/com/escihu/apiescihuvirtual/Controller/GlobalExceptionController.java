package com.escihu.apiescihuvirtual.Controller;

import com.escihu.apiescihuvirtual.Dto.ErrorDto;
import com.escihu.apiescihuvirtual.exceptions.EmailAlreadyTakenException;
import com.escihu.apiescihuvirtual.exceptions.UsernameAlreadyTakenException;
import com.escihu.apiescihuvirtual.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {


    @ExceptionHandler(value = UsernameAlreadyTakenException.class)
    public ResponseEntity<ErrorDto> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException e) {
        ErrorDto errorDto = new ErrorDto(400, e.getMessage(), "api/v1/auth/register", "Bad Request", e.getClass().getName(), java.time.Instant.now());
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(404, e.getMessage(), "api/v1/auth/login", "Not Found", e.getClass().getName(), java.time.Instant.now());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmailAlreadyTakenException.class)
    public ResponseEntity<ErrorDto> handleEmailAlreadyTakenException(EmailAlreadyTakenException e) {
        ErrorDto errorDto = new ErrorDto(400, e.getMessage(), "api/v1/auth/register", "Bad Request", e.getClass().getName(), java.time.Instant.now());
        return ResponseEntity.badRequest().body(errorDto);
    }

}
