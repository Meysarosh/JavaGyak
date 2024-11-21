package com.example.utazas;

public class UserNotFoundException extends RuntimeException {
     UserNotFoundException(int id) {
        super("Felhasználó azonosítóval: " + id + " nem található");
    }
}

