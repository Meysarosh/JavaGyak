package com.example.utazas;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserInsufficientDataException extends RuntimeException {
    UserInsufficientDataException() {
        super("Kevés vagy nem megfelelő adat.");
    }
}
