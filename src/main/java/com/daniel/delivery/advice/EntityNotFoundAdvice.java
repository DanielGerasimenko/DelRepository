package com.daniel.delivery.advice;

import com.daniel.delivery.exception.OrderNotFoundException;
import com.daniel.delivery.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class EntityNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler({OrderNotFoundException.class, PersonNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entityNotFoundHandler(Exception ex) {
        return ex.getMessage();
    }

}
