package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.PokemonCenter;
import com.fk.pokemoncare.entities.Type;

import java.util.List;

public interface TypeDao {
    Type getTypeByID (int id);
    List<Type> getAllTypes();
    Type addType(Type type);
    void updateType(Type type);
    void deleteTypeByID(int id);

    List<Type> getTypeByPokemon(Pokemon pokemon);
}
