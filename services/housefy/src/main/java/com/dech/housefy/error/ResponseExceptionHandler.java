package com.dech.housefy.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    @Value("${spring.application.name}")
    private String appName;

    @ExceptionHandler(value = { NullPointerException.class })
    public ResponseEntity handleNullPointerExceptions(
            RuntimeException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                appName,
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(value = { EmptyDataException.class })
//    public ResponseEntity handleEmptyDataExceptions(
//            RuntimeException ex, WebRequest request) {
//        ErrorResponse error = new ErrorResponse(
//                new Date(),
//                HttpStatus.BAD_REQUEST.value(),
//                HttpStatus.BAD_REQUEST.getReasonPhrase(),
//                ex.getMessage(),
//                appName,
//                request.getDescription(false)
//        );
//        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(value = { DataNotFoundException.class })
    public ResponseEntity handleDataNotFoundExceptions(
            RuntimeException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                appName,
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { NumberFormatException.class })
    public ResponseEntity handleNumberFormatExceptions(
            RuntimeException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                appName,
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
    public ResponseEntity handleMismatchExceptions(
            RuntimeException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                appName,
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DuplicateDataException.class})
    public ResponseEntity handleDuplicateDataExceptions(RuntimeException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
            new Date(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            ex.getMessage(),
            appName,
            request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(value = { UnauthorizedException.class })
//    public ResponseEntity handleUnauthorizedExceptions(
//            RuntimeException ex, WebRequest request) {
//        ErrorResponse error = new ErrorResponse(
//                new Date(),
//                HttpStatus.UNAUTHORIZED.value(),
//                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
//                ex.getMessage(),
//                appName,
//                request.getDescription(false)
//        );
//        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
//    }

    @ExceptionHandler(value = { InternalErrorException.class })
    public ResponseEntity handleInternalErrorExceptions(
            RuntimeException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                appName,
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
