package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.Trainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
//for a one to many relationship, keep the one simple. the many will handle it, in this case the pokemon.
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

    private static final String SELECT_POKEMONS_BY_TRAINER = "SELECT * FROM pokemon WHERE TrainerID = ?";



    public static final class TrainerMapper implements RowMapper<Trainer> {

        @Override
        public Trainer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trainer trainer = new Trainer();
            trainer.setName(rs.getString("TrainerName"));
            trainer.setAge(rs.getInt("TrainerAge"));
            trainer.setEmail(rs.getString("TrainerEmail"));
            trainer.setId(rs.getInt("TrainerID"));
            return trainer;
        }
    }

    //set objects to the Trainer.
    @Override
    public List<Trainer> getAllTrainers() {
        return jdbc.query(SELECT_ALL_TRAINERS, new TrainerMapper());
        }


    @Override
    public Trainer getTrainerById(int id) {
        try{
        return jdbc.queryForObject(SELECT_TRAINER_BY_ID, new TrainerMapper(), id);
        } catch (
                DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Trainer addTrainer(Trainer trainer) {
        jdbc.update(INSERT_TRAINER, trainer.getName(), trainer.getAge(), trainer.getEmail());
        // Get the auto-generated TrainerID
        Integer newTrainerId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        if (newTrainerId != null) {
            trainer.setId(newTrainerId);

        } else {
            throw new IllegalArgumentException("Failed to retrieve auto-generated TrainerID.");


        }
        return trainer;
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        jdbc.update(UPDATE_TRAINER, trainer.getName(), trainer.getAge(), trainer.getEmail(), trainer.getId());
    }
//delete from the reference table first
    @Override
    public void deleteTrainer(int id) {
        // Delete associated records in the Pokemon table
        jdbc.update(DELETE_TRAINER_FROM_POKEMON, id);

        // Delete the Trainer record
        jdbc.update(DELETE_TRAINER, id);
    }

}
