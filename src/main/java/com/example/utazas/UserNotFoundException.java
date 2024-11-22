package com.example.utazas;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
     UserNotFoundException(int id) {
        super("Felhasználó azonosítóval: " + id + " nem található");
    }
}

