package com.multigen.main.Utility;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.multigen.main.Exception.AppExceptions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobelExceptionHandler {

    @Autowired
    private Environment environment;

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalException(Exception exception){
        ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorInfo,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppExceptions.class)
    public ResponseEntity<ErrorInfo> generalException(AppExceptions exception){

        String message;

        message = environment.getProperty(exception.getMessage());

        ErrorInfo errorInfo = new ErrorInfo(message, HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorInfo,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> ValidationExceptionHandler(Exception exception){

        String message;

        if (exception instanceof MethodArgumentNotValidException manvException){

            message = manvException.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
        }else {

            ConstraintViolationException cvException = (ConstraintViolationException) exception;

            message = cvException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
        }

        ErrorInfo errorInfo = new ErrorInfo(message,HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());

        return new ResponseEntity<>(errorInfo,HttpStatus.BAD_REQUEST);
    }

}
