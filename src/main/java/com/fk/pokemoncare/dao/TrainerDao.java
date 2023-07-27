package com.fk.pokemoncare.dao;
import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.Trainer;

import java.util.List;

public interface TrainerDao {
    Trainer getTrainerById(int id);

    List<Trainer> getAllTrainers();

    Trainer addTrainer(Trainer trainer);

    void updateTrainer(Trainer trainer);

    void deleteTrainer(int id);

    List<Pokemon> getPokemonListByTrainer(Trainer trainer);
}

