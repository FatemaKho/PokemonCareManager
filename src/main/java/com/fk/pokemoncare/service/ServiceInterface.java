package com.fk.pokemoncare.service;

import com.fk.pokemoncare.entities.*;

import java.util.List;

public interface ServiceInterface {

    HealthRecord getHealthRecordById(int id);

    List<HealthRecord> getAllHealthRecords();

    HealthRecord addHealthRecord(HealthRecord healthRecord);

    void updateHealthRecord(HealthRecord healthRecord);

    void deleteHealthRecordById(int id);

    List<HealthRecord> getAllHealthRecordsByPokemonCenter(int pokemonCenterId);
//POKEMON CENTER
    PokemonCenter getCenterById(int id);

    List<PokemonCenter> getAllCenters();

    PokemonCenter addCenter(PokemonCenter center) throws DuplicateNameExistsException;

    void updateCenter(PokemonCenter center);

    void deleteCenterById(int id);
    //pokemon

    List<Pokemon> getAllPokemon();

    Pokemon getPokemonById(int id);

    Pokemon addPokemon(Pokemon pokemon);

    void updatePokemon(Pokemon pokemon);

    void deletePokemonById(int id);

    List<Pokemon> getPokemonByTrainerId(Trainer trainer);

    //for lists, use objects FIX THIS so that you pass Type object and make sure sql matches***
    List<Pokemon> getPokemonByTypeId(Type type);

    // TRAINER
    Trainer getTrainerById(int id);

    List<Trainer> getAllTrainers();

    Trainer addTrainer(Trainer trainer) throws DuplicateEmailExistsException;

    void updateTrainer(Trainer trainer);

    void deleteTrainer(int id);

//TYPESd
    Type getTypeByID (int id);
    List<Type> getAllTypes();
    Type addType(Type type);
    void updateType(Type type);
    void deleteTypeByID(int id);

    List<Type> getTypeByPokemon(Pokemon pokemon);
}
