package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.entities.PokemonCenter;

import java.util.List;

public interface PokemonCenterDao {
    PokemonCenter getCenterById(int id);

    List<PokemonCenter> getAllCenters();

    PokemonCenter addCenter(PokemonCenter center);

    void updateCenter(PokemonCenter center);

    void deleteCenterById(int id);
}
