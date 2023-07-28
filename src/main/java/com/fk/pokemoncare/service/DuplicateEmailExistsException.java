package com.fk.pokemoncare.service;

public class DuplicateEmailExistsException extends Throwable {
    public DuplicateEmailExistsException(String message) {
        super(message);
    }
}
