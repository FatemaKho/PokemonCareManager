package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.dao.PokemonCenterDaoDB;
import com.fk.pokemoncare.dao.PokemonDaoDB;
import com.fk.pokemoncare.dao.TrainerDaoDB;
import com.fk.pokemoncare.dao.TypeDaoDB;
import com.fk.pokemoncare.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PokemonDaoDBTest {

    @Autowired
    private PokemonDaoDB pokemonDao;

    @Autowired
    private TrainerDaoDB trainerDao;

    @Autowired
    private TypeDaoDB typeDao;

    @Autowired
    private PokemonCenterDaoDB pokemonCenterDao;

    @Autowired HealthRecordDaoDB healthRecordDao;

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
    public void testAddPokemonToPokemonType() {
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

        pokemon.setTypes(List.of(electricType, normalType)); // Add both types to the Pokemon

        // Save the Pokemon to the database
        Pokemon addedPokemon = pokemonDao.addPokemon(pokemon);

        // Retrieve the types associated with the Pokemon
        List<Type> typesForPokemon = typeDao.getTypeByPokemon(addedPokemon);

        // Verify that the retrieved types match the original types
        Assertions.assertNotNull(typesForPokemon);
        Assertions.assertEquals(2, typesForPokemon.size());

        // Check if both types are present in the retrieved types
        boolean hasElectricType = false;
        boolean hasNormalType = false;

        for (Type type : typesForPokemon) {
            if (type.getName().equals("Electric")) {
                hasElectricType = true;
            } else if (type.getName().equals("Normal")) {
                hasNormalType = true;
            }
        }

        Assertions.assertTrue(hasElectricType);
        Assertions.assertTrue(hasNormalType);
    }



    @Test
    public void testAddPokemon() {
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

        // Set the Pokemon Type
        Type electricType = new Type();
        electricType.setName("Electric");
        electricType = typeDao.addType(electricType);
        pokemon.setTypes(List.of(electricType));

        // Save the Pokemon to the database
        Pokemon addedPokemon = pokemonDao.addPokemon(pokemon);

        // Verify that the addedPokemon has an ID assigned (not null)
        Assertions.assertNotNull(addedPokemon.getId());
    }

    @Test
    public void testGetPokemonById() {
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

        // Set the Pokemon Type
        Type electricType = new Type();
        electricType.setName("Electric");
        electricType = typeDao.addType(electricType);
        pokemon.setTypes(List.of(electricType));

        // Save the Pokemon to the database
        Pokemon addedPokemon = pokemonDao.addPokemon(pokemon);

        // Retrieve the Pokemon from the database using its ID
        Pokemon retrievedPokemon = pokemonDao.getPokemonById(addedPokemon.getId());

        // Assertions to check if the retrieved Pokemon is the same as the added one
        Assertions.assertNotNull(retrievedPokemon);
        Assertions.assertEquals("Pikachu", retrievedPokemon.getName());
        Assertions.assertEquals("Electric Mouse Pokemon", retrievedPokemon.getSpecies());
        Assertions.assertEquals(15, retrievedPokemon.getLevel());
        Assertions.assertEquals(trainer.getName(), retrievedPokemon.getTrainer().getName());
    }

    @Test
    public void testGetAllPokemon() {
        // Create sample Trainer
        Trainer trainer = new Trainer();
        trainer.setName("Ash Ketchum");
        trainer.setAge(10);
        trainer.setEmail("ash@example.com");
        trainer = trainerDao.addTrainer(trainer);

        // Create sample Pokemon
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setName("Pikachu");
        pokemon1.setSpecies("Electric Mouse Pokemon");
        pokemon1.setLevel(15);
        pokemon1.setTrainer(trainer);

        // Set the Pokemon Type
        Type electricType = new Type();
        electricType.setName("Electric");
        electricType = typeDao.addType(electricType);
        pokemon1.setTypes(List.of(electricType));

        pokemon1 = pokemonDao.addPokemon(pokemon1);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setName("Charmander");
        pokemon2.setSpecies("Lizard Pokemon");
        pokemon2.setLevel(12);
        pokemon2.setTrainer(trainer);

        // Set the Pokemon Type
        Type fireType = new Type();
        fireType.setName("Fire");
        fireType = typeDao.addType(fireType);
        pokemon2.setTypes(List.of(fireType));

        pokemon2 = pokemonDao.addPokemon(pokemon2);

        // Call the method to get all Pokemon
        List<Pokemon> allPokemon = pokemonDao.getAllPokemon();

        // Verify that the expected and actual lists of Pokemon are the same
        Assertions.assertEquals(2, allPokemon.size());
        Assertions.assertTrue(allPokemon.contains(pokemon1));
        Assertions.assertTrue(allPokemon.contains(pokemon2));
    }

    @Test
    public void testUpdatePokemon() {
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

        // Set the Pokemon Type
        Type electricType = new Type();
        electricType.setName("Electric");
        electricType = typeDao.addType(electricType);
        pokemon.setTypes(List.of(electricType));

        // Save the Pokemon to the database
        Pokemon addedPokemon = pokemonDao.addPokemon(pokemon);

        // Retrieve the Pokemon from the database using its ID
        Pokemon retrievedPokemon = pokemonDao.getPokemonById(addedPokemon.getId());

        // Assertions before the update
        Assertions.assertNotNull(retrievedPokemon);
        Assertions.assertEquals(15, retrievedPokemon.getLevel());

        // Update the Pokemon's level
        retrievedPokemon.setLevel(20);

        // Update the Pokemon in the database
        pokemonDao.updatePokemon(retrievedPokemon);

        // Retrieve the Pokemon again after the update
        Pokemon updatedPokemon = pokemonDao.getPokemonById(addedPokemon.getId());

        // Assertions after the update
        Assertions.assertNotNull(updatedPokemon);
        Assertions.assertEquals(20, updatedPokemon.getLevel());
    }

    @Test
    public void testDeletePokemonById() {
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

        // Set the Pokemon Type
        Type electricType = new Type();
        electricType.setName("Electric");
        electricType = typeDao.addType(electricType);
        pokemon.setTypes(List.of(electricType));

        // Save the Pokemon to the database
        Pokemon addedPokemon = pokemonDao.addPokemon(pokemon);

        // Call the method to delete the Pokemon by ID
        pokemonDao.deletePokemonById(addedPokemon.getId());

        // Attempt to retrieve the Pokemon after deletion
        Pokemon deletedPokemon = pokemonDao.getPokemonById(addedPokemon.getId());

        // The deleted Pokemon should be null
        Assertions.assertNull(deletedPokemon);
    }
}


