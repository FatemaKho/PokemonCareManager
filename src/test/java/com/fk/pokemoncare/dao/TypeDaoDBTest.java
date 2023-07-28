package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
@SpringBootTest
public class TypeDaoDBTest {
    @Autowired
    private TrainerDaoDB trainerDao;

    @Autowired
    private PokemonDaoDB pokemonDao;

    @Autowired
    private TypeDaoDB typeDao;

    @Autowired
    private PokemonCenterDaoDB pokemonCenterDao;

    @Autowired
    HealthRecordDaoDB healthRecordDao;

    @BeforeEach
        public void setUp() {
            List<HealthRecord> healthRecords = healthRecordDao.getAllHealthRecords();
            for (HealthRecord healthRecord : healthRecords) {
                healthRecordDao.deleteHealthRecordById(healthRecord.getId());
            }

            // Delete pokemons
            List<Pokemon> pokemons = pokemonDao.getAllPokemon();
            for (Pokemon pokemon : pokemons) {
                pokemonDao.deletePokemonById(pokemon.getId());
            }

            // Delete centers
            List<PokemonCenter> centers = pokemonCenterDao.getAllCenters();
            for (PokemonCenter center : centers) {
                pokemonCenterDao.deleteCenterById(center.getId());
            }

            // Delete trainers
            List<Trainer> trainers = trainerDao.getAllTrainers();
            for (Trainer trainer : trainers) {
                trainerDao.deleteTrainer(trainer.getId());
            }

            // Delete types
            List<Type> types = typeDao.getAllTypes();
            for (Type type : types) {
                typeDao.deleteTypeByID(type.getId());
            }

        }
    @Test
    public void testGetTypeByID() {
        Type type = new Type();
        type.setName("Fire");
        type = typeDao.addType(type);

        Type retrievedType = typeDao.getTypeByID(type.getId());
        Assertions.assertNotNull(retrievedType);
        Assertions.assertEquals("Fire", retrievedType.getName());
    }

    @Test
    public void testGetAllTypes() {
        Type type1 = new Type();
        type1.setName("Fire");
        typeDao.addType(type1);

        Type type2 = new Type();
        type2.setName("Water");
        typeDao.addType(type2);

        List<Type> types = typeDao.getAllTypes();
        Assertions.assertEquals(2, types.size());
    }

    @Test
    public void testGetTypeByPokemon() {
        // Create a sample Trainer
        Trainer trainer = new Trainer();
        trainer.setName("Ash Ketchum");
        trainer.setAge(10);
        trainer.setEmail("ash@example.com");
        trainer = trainerDao.addTrainer(trainer);

        // Create a sample Pokemon
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Pikachu");
        pokemon.setSpecies("Electric Mouse Pokemon");
        pokemon.setLevel(15);
        pokemon.setTrainer(trainer);

        // Set the Pokemon Types
        Type electricType = new Type();
        electricType.setName("Electric");
        electricType = typeDao.addType(electricType);

        Type normalType = new Type();
        normalType.setName("Normal");
        normalType = typeDao.addType(normalType);

        List<Type> types = new ArrayList<>();
        types.add(electricType);
        types.add(normalType);

        pokemon.setTypes(types);

        // Save the Pokemon to the database
        Pokemon addedPokemon = pokemonDao.addPokemon(pokemon);

        // Retrieve the types associated with the Pokemon
        List<Type> typesForPokemon = typeDao.getTypeByPokemon(addedPokemon);

        // Verify that the retrieved types match the original types
        Assertions.assertNotNull(typesForPokemon);
        Assertions.assertEquals(2, typesForPokemon.size());
        Assertions.assertEquals("Electric", typesForPokemon.get(0).getName());
        Assertions.assertEquals("Normal", typesForPokemon.get(1).getName());
    }


    @Test
    public void testUpdateType() {
        Type type = new Type();
        type.setName("Fire");
        type = typeDao.addType(type);

        type.setName("Updated Type");
        typeDao.updateType(type);

        Type updatedType = typeDao.getTypeByID(type.getId());
        Assertions.assertNotNull(updatedType);
        Assertions.assertEquals("Updated Type", updatedType.getName());
    }

    @Test
    public void testDeleteTypeByID() {
        Type type = new Type();
        type.setName("Fire");
        type = typeDao.addType(type);

        typeDao.deleteTypeByID(type.getId());

        Type deletedType = typeDao.getTypeByID(type.getId());
        Assertions.assertNull(deletedType);
    }
}

