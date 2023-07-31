package com.fk.pokemoncare.dao;


import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.Trainer;
import com.fk.pokemoncare.entities.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PokemonDaoDB implements PokemonDao {

    private static final String SELECT_ALL_POKEMON = "SELECT * FROM pokemon";
    private static final String SELECT_POKEMON_BY_ID = "SELECT * FROM pokemon WHERE PokemonID = ?";
    private static final String INSERT_POKEMON = "INSERT INTO pokemon (PokemonName, Species, PokemonLevel, TrainerID) VALUES (?, ?, ?, ?)";
    private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    private static final String UPDATE_POKEMON = "UPDATE pokemon SET PokemonName = ?, Species = ?, PokemonLevel = ?, TrainerID = ? WHERE PokemonID = ?";
    private static final String DELETE_POKEMON = "DELETE FROM pokemon WHERE PokemonID = ?";
    private static final String SELECT_POKEMON_BY_TRAINER_ID = "SELECT * FROM pokemon WHERE TrainerID = ?";
    private static final String SELECT_POKEMON_BY_TYPE_ID = "SELECT p.* FROM pokemon p " +
            "INNER JOIN pokemontype pt ON p.PokemonID = pt.PokemonID " +
            "WHERE pt.TypeID = ?";
    private static final String SELECT_TYPES_BY_POKEMON_ID = "SELECT t.* FROM type t " +
            "INNER JOIN pokemontype pt ON t.TypeID = pt.TypeID " +
            "WHERE pt.PokemonID = ?";

    private static final String SELECT_TRAINER_FOR_POKEMON = "SELECT t.* FROM trainer t " +
            "INNER JOIN pokemon p ON t.TrainerID = p.TrainerID " +
            "WHERE p.PokemonID = ?";
    private static final String DELETE_ASSOCIATED_POKEMON_TYPES = "DELETE FROM pokemontype WHERE PokemonID = ?";
    private static final String DELETE_ASSOCIATED_HEALTH_RECORDS = "DELETE FROM healthrecord WHERE PokemonID = ?";

    @Autowired
    JdbcTemplate jdbc;

    //set the objects to pokemon here too
    @Override
    public List<Pokemon> getAllPokemon() {
        List<Pokemon> pokemons= jdbc.query(SELECT_ALL_POKEMON, new PokemonMapper());
        if(pokemons!=null) {
            for (Pokemon pokemon : pokemons) {
                pokemon.setTypes(getTypesForPokemon(pokemon));
                if(getTrainerForPokemon(pokemon.getId())!=null) {
                    pokemon.setTrainer(getTrainerForPokemon(pokemon.getId()));//helper
                } else {
                    pokemon.setTrainer(null);
                }
                pokemon.setTrainer(getTrainerForPokemon(pokemon.getId()));
            }

            return pokemons;
        }
        return null;
    }

    //set objects to pokemon using helpermethods
    @Override
    public Pokemon getPokemonById(int id) {
        try {
        Pokemon pokemon = jdbc.queryForObject(SELECT_POKEMON_BY_ID, new PokemonMapper(), id);
        List<Type> types = getTypesForPokemon(pokemon); //helper
        pokemon.setTypes(types);
        if(getTrainerForPokemon(id)!=null) {
            pokemon.setTrainer(getTrainerForPokemon(id));//helper
        } else {
            pokemon.setTrainer(null);
        }
        return pokemon;
        } catch (
                DataAccessException ex) {
            return null;
        }
    }

    //YOU NEEED THE LAST INSERTED ID BECAUSE ITS AUTO INCREMENTED and set it's id
    @Override
    public Pokemon addPokemon(Pokemon pokemon) {
        jdbc.update(INSERT_POKEMON, pokemon.getName(), pokemon.getSpecies(), pokemon.getLevel(), pokemon.getTrainer().getId());

        int newPokemonId = jdbc.queryForObject(SELECT_LAST_INSERT_ID, Integer.class);
        pokemon.setId(newPokemonId);
        addPokemonToPokemonType(pokemon);

        return pokemon;    }

    @Override
    public void updatePokemon(Pokemon pokemon) {
        jdbc.update(UPDATE_POKEMON, pokemon.getName(), pokemon.getSpecies(), pokemon.getLevel(), pokemon.getTrainer().getId(), pokemon.getId());
        // Remove existing associations with Type
        jdbc.update(DELETE_ASSOCIATED_POKEMON_TYPES, pokemon.getId());

        // Add new associations with Type
        addPokemonToPokemonType(pokemon);

    }
//make sure you delete reference tables first
    @Override
    public void deletePokemonById(int id) {
        // Delete associated records in the pokemontype table
        jdbc.update(DELETE_ASSOCIATED_POKEMON_TYPES, id);

        // Delete associated records in the healthrecord table
        jdbc.update(DELETE_ASSOCIATED_HEALTH_RECORDS, id);

        // If there are other related tables, delete records from them as well

        // Then, delete the Pokemon record
        jdbc.update(DELETE_POKEMON, id);
    }

    @Override
    public List<Pokemon> getPokemonByTrainerId(Trainer trainer) {
        return jdbc.query(SELECT_POKEMON_BY_TRAINER_ID, new PokemonMapper(), trainer.getId());
    }

    //for lists, use objects FIX THIS so that you pass Type object and make sure sql matches***
    @Override
    public List<Pokemon> getPokemonByTypeId(Type type) {
        return jdbc.query(SELECT_POKEMON_BY_TYPE_ID, new PokemonMapper(), type.getId());
    }
    private List<Type> getTypesForPokemon(Pokemon pokemon) {
        List<Type> pokemonTypes= jdbc.query(SELECT_TYPES_BY_POKEMON_ID, new TypeDaoDB.TypeMapper(), pokemon.getId());
        return pokemonTypes;
    }

    private Trainer getTrainerForPokemon(int id) {
        try {
        return jdbc.queryForObject(SELECT_TRAINER_FOR_POKEMON, new TrainerDaoDB.TrainerMapper(), id);
        } catch (
                DataAccessException ex) {
            return null;
        }
    }
    void addPokemonToPokemonType(Pokemon pokemon) {
        String insertPokemonTypeSql = "INSERT INTO pokemontype (PokemonID, TypeID) VALUES (?, ?)";

        for (Type type : pokemon.getTypes()) {
            jdbc.update(insertPokemonTypeSql, pokemon.getId(), type.getId());
        }

    }


    public static final class PokemonMapper implements RowMapper<Pokemon> {

        @Override
        public Pokemon mapRow(ResultSet rs, int rowNum) throws SQLException {
            Pokemon pokemon = new Pokemon();
            pokemon.setId(rs.getInt("PokemonID"));
            pokemon.setName(rs.getString("PokemonName"));
            pokemon.setSpecies(rs.getString("Species"));
            pokemon.setLevel(rs.getInt("PokemonLevel"));
            //trainer and type are being set elsewhere because they're objects
            return pokemon;
        }


    }
}
