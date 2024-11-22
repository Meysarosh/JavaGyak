package com.example.utazas;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
    UserAlreadyExistsException(String email){
        super("Felhasználó " + email + " email cimmel már létezik'");
    }
}