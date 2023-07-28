package com.fk.pokemoncare.service;

import com.fk.pokemoncare.dao.*;
import com.fk.pokemoncare.entities.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceLayer implements ServiceInterface {
    @Autowired
    private HealthRecordDaoDB healthRecordDao;

    @Autowired
    private PokemonDaoDB pokemonDao;

    @Autowired
    private PokemonCenterDaoDB pokemonCenterDao;

    @Autowired
    private TrainerDaoDB trainerDao;

    @Autowired
    private TypeDaoDB typeDao;
//HEALTH RECORD DAO

    @Override
    public HealthRecord getHealthRecordById(int id) {
        return healthRecordDao.getHealthRecordById(id);
    }

    @Override
    public List<HealthRecord> getAllHealthRecords() {
        return healthRecordDao.getAllHealthRecords();
    }

    @Override
    public HealthRecord addHealthRecord(HealthRecord healthRecord) {
        return healthRecordDao.addHealthRecord(healthRecord);
    }

    @Override
    public void updateHealthRecord(HealthRecord healthRecord) {
        healthRecordDao.updateHealthRecord(healthRecord);
    }

    @Override
    public void deleteHealthRecordById(int id) {
        healthRecordDao.deleteHealthRecordById(id);
    }
    @Override
    public List<HealthRecord> getAllHealthRecordsByPokemonCenter(int pokemonCenterId) {
        return healthRecordDao.getAllHealthRecordsByPokemonCenter(pokemonCenterId);

    }
//POKEMONCENTERDAO
    @Override
    public PokemonCenter getCenterById(int id) {
        return pokemonCenterDao.getCenterById(id);
    }

    @Override
    public List<PokemonCenter> getAllCenters() {
        return pokemonCenterDao.getAllCenters();
    }

    @Override
    public PokemonCenter addCenter(PokemonCenter center) throws DuplicateNameExistsException {
        validatePokemonCenter(center);
        return pokemonCenterDao.addCenter(center);
    }

    @Override
    public void updateCenter(PokemonCenter center) {
        pokemonCenterDao.updateCenter(center);
    }

    @Override
    public void deleteCenterById(int id) {
        pokemonCenterDao.deleteCenterById(id);

    }
    public void validatePokemonCenter(PokemonCenter center) throws DuplicateNameExistsException {
        List<PokemonCenter> centers = getAllCenters();
        boolean isDuplicate = false;
        for (PokemonCenter existingCenter : centers) {
            if (existingCenter.getName().equalsIgnoreCase(center.getName())) {
                isDuplicate = true;
                break;
            }
        }
        if (isDuplicate) {
            throw new DuplicateNameExistsException("Pokemon Center with the same name already exists in the system.");
        }
    }
    //POKEMONDAO

    @Override
    public List<Pokemon> getAllPokemon() {
        return pokemonDao.getAllPokemon();
    }

    @Override
    public Pokemon getPokemonById(int id) {
        return pokemonDao.getPokemonById(id);
    }

    @Override
    public Pokemon addPokemon(Pokemon pokemon) {
        return pokemonDao.addPokemon(pokemon);
    }

    @Override
    public void updatePokemon(Pokemon pokemon) {
        pokemonDao.updatePokemon(pokemon);
    }

    @Override
    public void deletePokemonById(int id) {
        pokemonDao.deletePokemonById(id);
    }

    @Override
    public List<Pokemon> getPokemonByTrainerId(Trainer trainer) {
        return pokemonDao.getPokemonByTrainerId(trainer);
    }

    @Override
    public List<Pokemon> getPokemonByTypeId(Type type) {
        return pokemonDao.getPokemonByTypeId(type);
    }

    @Override
    public Trainer getTrainerById(int id) {
        return trainerDao.getTrainerById(id);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDao.getAllTrainers();
    }

    @Override
    public Trainer addTrainer(Trainer trainer) throws DuplicateEmailExistsException {
        validateTrainer(trainer);
        return trainerDao.addTrainer(trainer);
    }
    public void validateTrainer(Trainer trainer) throws DuplicateEmailExistsException {
        List<Trainer> trainers = getAllTrainers();
        boolean isDuplicate = false;
        for (Trainer existingTrainer : trainers) {
            if (existingTrainer.getEmail().equalsIgnoreCase(trainer.getEmail())) {
                isDuplicate = true;
                break;
            }
        }
        if (isDuplicate) {
            throw new DuplicateEmailExistsException("Trainer with the same email already exists in the system.");
        }
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        trainerDao.updateTrainer(trainer);
    }

    @Override
    public void deleteTrainer(int id) {
    trainerDao.deleteTrainer(id);
    }
//TYPE
    @Override
    public Type getTypeByID(int id) {
        return typeDao.getTypeByID(id);
    }

    @Override
    public List<Type> getAllTypes() {
        return typeDao.getAllTypes();
    }

    @Override
    public Type addType(Type type) {
       return typeDao.addType(type);
    }

    @Override
    public void updateType(Type type) {
        typeDao.updateType(type);

    }

    @Override
    public void deleteTypeByID(int id) {
        typeDao.deleteTypeByID(id);
    }

    @Override
    public List<Type> getTypeByPokemon(Pokemon pokemon) {
        return typeDao.getTypeByPokemon(pokemon);
    }

    public void validateType(Type type) throws DuplicateNameExistsException {
        List<Type> existingTypes = typeDao.getAllTypes();
        for (Type existingType : existingTypes) {
            if (existingType.getName().equalsIgnoreCase(type.getName())) {
                throw new DuplicateNameExistsException("Type with name already exists: " + type.getName());
            }
        }
    }



}
