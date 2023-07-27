package com.fk.pokemoncare.dao;


import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.Trainer;
import com.fk.pokemoncare.entities.Type;

import java.util.List;

public interface PokemonDao {
    List<Pokemon> getAllPokemon();

    Pokemon getPokemonById(int id);

    Pokemon addPokemon(Pokemon pokemon);

    void updatePokemon(Pokemon pokemon);

    void deletePokemonById(int id);

    List<Pokemon> getPokemonByTrainerId(Trainer trainer);

    //for lists, use objects FIX THIS so that you pass Type object and make sure sql matches***
    List<Pokemon> getPokemonByTypeId(Type type);
}
