package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.Type;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.convert.TypeMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TypeDaoDB implements TypeDao {

    @Autowired
    JdbcTemplate jdbc;

    final String GET_TYPE_BY_ID = "SELECT * FROM type WHERE typeID = ?";
    final String GET_POKEMONS_BY_TYPE = "SELECT DISTINCT p.* FROM pokemon p INNER JOIN pokemontype pt " +
            " ON p.PokemonID = pt.PokemonID WHERE TypeID = ?";
    final String GET_ALL_TYPES = "SELECT * FROM type";
    final String INSERT_TYPE = "INSERT INTO type (TypeName) VALUES(?)";
    final String GET_INSERT_ID = "SELECT LAST_INSERT_ID()";
    final String UPDATE_TYPE = "UPDATE type SET TypeName = ?, WHERE TypeID = ?";
    final String DELETE_POKEMON_TYPE = "DELETE FROM pokemontype WHERE TypeID = ?";
    final String DELETE_TYPE = "DELETE FROM type WHERE TypeID = ?";
    final String GET_TYPES_BY_POKEMON = "SELECT t.* FROM type t INNER JOIN pokemontype pt ON " +
            " t.TypeID = pt.TypeID WHERE pt.PokemonID = ?";


        @Override
    public Type getTypeByID(int id) {
        try {
            Type type = jdbc.queryForObject(GET_TYPE_BY_ID, new TypeMapper(), id);
            //create a helper method to get a list of pokemons by type to set it
            List<Pokemon> pokemons = getPokemonsByType(type);
            return type;
        } catch (DataAccessException e) {
            return null;
        }
    }
    private List<Pokemon> getPokemonsByType(Type type) {
        return jdbc.query(GET_POKEMONS_BY_TYPE, new PokemonDaoDB.PokemonMapper(),type.getId());
    }

    @Override
    public List<Type> getAllTypes() {
        //loop through list of types, and setPokemons for each type object.
        List<Type> types= jdbc.query(GET_ALL_TYPES, new TypeMapper());
        for(Type type: types) {
            List<Pokemon> pokemons = getPokemonsByType(type);
            type.setPokemons(pokemons);
        }
        return types;
    }

    @Override
    public Type addType(Type type) {
        try {
            jdbc.update(INSERT_TYPE, type.getName());
            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            type.setId(newId);
            return type;
        } catch (DataAccessException ex) {
            // Handle the exception by printing a custom message
            System.err.println("Error while adding type: " + ex.getMessage());
            // Optionally, you can throw a custom exception to indicate the failure
            // throw new MyCustomException("Failed to add type", ex);
            return null; // or return some default value or null to indicate failure
        }
    }


    @Override
    public void updateType(Type type) {
        jdbc.update(UPDATE_TYPE,
                type.getName());
    }


    @Override
    public void deleteTypeByID(int id) {
        jdbc.update(DELETE_POKEMON_TYPE, id);
        jdbc.update(DELETE_TYPE, id);
    }

    @Override
    public List<Type> getTypeByPokemon(Pokemon pokemon) {
        List<Type> types = jdbc.query(GET_TYPES_BY_POKEMON, new TypeMapper(), pokemon.getId());
        for (Type type : types) {
            type.setPokemons(getPokemonsByType(type));
        }
        return types;

    }

    public static class TypeMapper implements RowMapper<Type> {

        @Override
        public Type mapRow(ResultSet rs, int rowNum) throws SQLException {
            Type type = new Type();
            type.setId(rs.getInt("TypeID"));
            type.setName(rs.getString("TypeName"));
            return type;
        }
    }


    }



