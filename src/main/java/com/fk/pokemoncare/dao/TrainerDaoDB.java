package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.Trainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class TrainerDaoDB implements TrainerDao {

    @Autowired
    JdbcTemplate jdbc;

    // SQL queries

    private static final String SELECT_ALL_TRAINERS = "SELECT * FROM trainer";
    private static final String SELECT_TRAINER_BY_ID = "SELECT * FROM trainer WHERE TrainerID = ?";
    private static final String INSERT_TRAINER = "INSERT INTO trainer (TrainerName, TrainerAge, TrainerEmail) VALUES (?, ?, ?)";
    private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

    private static final String UPDATE_TRAINER = "UPDATE trainer SET TrainerName = ?, TrainerAge = ?, TrainerEmail = ? WHERE TrainerID = ?";
    private static final String DELETE_TRAINER = "DELETE FROM trainer WHERE TrainerID = ?";
    private static final String DELETE_TRAINER_FROM_POKEMON = "UPDATE pokemon SET TrainerID = NULL WHERE TrainerID = ?";
    private static final String UPDATE_POKEMON = "UPDATE pokemon SET PokemonName = ?, Species = ?, PokemonLevel = ?, TrainerID = ? WHERE PokemonID = ?";

    private static final String SELECT_POKEMONS_BY_TRAINER = "SELECT * FROM pokemon WHERE TrainerID = ?";

    public static final class TrainerMapper implements RowMapper<Trainer> {
        @Override
        public Trainer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trainer trainer = new Trainer();
            trainer.setId(rs.getInt("TrainerID"));
            trainer.setName(rs.getString("TrainerName"));
            trainer.setAge(rs.getInt("TrainerAge"));
            trainer.setEmail(rs.getString("TrainerEmail"));
            return trainer;
        }
    }

    @Override
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = jdbc.query(SELECT_ALL_TRAINERS, new TrainerMapper());
        for (Trainer trainer : trainers) {
            // Get the list of pokemons for each trainer and set it
            List<Pokemon> pokemons = getPokemonsByTrainer(trainer);
            trainer.setPokemons(pokemons);
        }
        return trainers;
    }

    @Override
    public Trainer getTrainerById(int id) {
        try {
            Trainer trainer = jdbc.queryForObject(SELECT_TRAINER_BY_ID, new TrainerMapper(), id);
            // Get the list of pokemons for the trainer and set it
            List<Pokemon> pokemons = getPokemonsByTrainer(trainer);
            trainer.setPokemons(pokemons);
            return trainer;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Trainer addTrainer(Trainer trainer) {
        jdbc.update(INSERT_TRAINER, trainer.getName(), trainer.getAge(), trainer.getEmail());
        // Get the auto-generated TrainerID
        Integer newTrainerId = jdbc.queryForObject(SELECT_LAST_INSERT_ID, Integer.class);
        if (newTrainerId != null) {
            trainer.setId(newTrainerId);

            // Get the list of pokemons for the trainer and set it
            List<Pokemon> pokemons = getPokemonsByTrainer(trainer);
            trainer.setPokemons(pokemons);
        } else {
            throw new IllegalArgumentException("Failed to retrieve auto-generated TrainerID.");
        }
        return trainer;
    }


    @Override
    @Transactional
    public void updateTrainer(Trainer trainer) {
        jdbc.update(UPDATE_TRAINER, trainer.getName(), trainer.getAge(), trainer.getEmail(), trainer.getId());
        for (Pokemon pokemon : trainer.getPokemons()) {
            jdbc.update(UPDATE_POKEMON, pokemon.getName(), pokemon.getSpecies(), pokemon.getLevel(), pokemon.getTrainer().getId(), pokemon.getId());
        }
    }


    @Override
    public void deleteTrainer(int id) {
        // Delete associated records in the Pokemon table
        jdbc.update(DELETE_TRAINER_FROM_POKEMON, id);

        // Delete the Trainer record
        jdbc.update(DELETE_TRAINER, id);
    }

    private List<Pokemon> getPokemonsByTrainer(Trainer trainer) {
        return jdbc.query(SELECT_POKEMONS_BY_TRAINER, new PokemonDaoDB.PokemonMapper(), trainer.getId());
    }
}
