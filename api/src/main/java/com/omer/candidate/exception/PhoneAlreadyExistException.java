package com.omer.candidate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PhoneAlreadyExistException extends RuntimeException{

    public PhoneAlreadyExistException(String message) {
        super(message);
    }

}
