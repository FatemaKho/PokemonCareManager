package com.fk.pokemoncare.service;

public class DuplicateNameExistsException extends Throwable {
    public DuplicateNameExistsException(String message) {
        super(message);
    }
}
