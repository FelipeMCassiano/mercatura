package com.felipemcassiano.Mercatura.infra.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ErrorMessageDTO> handleUserNotFound(Exception e) {
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityConflictException.class)
    public ResponseEntity<ErrorMessageDTO> handleUserConflict(Exception e) {
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ErrorMessageDTO> handleInternal() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
