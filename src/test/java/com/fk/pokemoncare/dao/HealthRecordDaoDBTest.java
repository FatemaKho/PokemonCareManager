package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.dao.*;
import com.fk.pokemoncare.entities.HealthRecord;
import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.PokemonCenter;
import com.fk.pokemoncare.entities.Trainer;
import com.fk.pokemoncare.entities.Type;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class HealthRecordDaoDBTest {
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
    public void testAddHealthRecord() {
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
        pokemon = pokemonDao.addPokemon(pokemon);

        // Create a sample PokemonCenter
        PokemonCenter pokemonCenter = new PokemonCenter();
        pokemonCenter.setName("Pallet Town Pokecenter");
        pokemonCenter.setAddress("123 Oak Street");
        pokemonCenter = pokemonCenterDao.addCenter(pokemonCenter);

        // Create a sample HealthRecord object to add
        HealthRecord healthRecordToAdd = new HealthRecord();
        healthRecordToAdd.setDescription("Feeling GUUUUD");
        healthRecordToAdd.setDate(LocalDateTime.of(2023, 7, 20, 16, 45, 0));
        healthRecordToAdd.setPokemon(pokemon);
        healthRecordToAdd.setPokemonCenter(pokemonCenter);

        // Add the HealthRecord to the database
        HealthRecord addedHealthRecord = healthRecordDao.addHealthRecord(healthRecordToAdd);

        // Verify that the addedHealthRecord has an ID assigned (not null)
        Assertions.assertNotNull(addedHealthRecord.getId());
    }

    @Test
    public void testGetHealthRecordById() {
        // Create a sample Trainer
        Trainer trainer = new Trainer();
        trainer.setName("Ash Ketchum");
        trainer.setAge(11);
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

        // Create a sample PokemonCenter
        PokemonCenter pokemonCenter = new PokemonCenter();
        pokemonCenter.setName("Pallet Town Pokecenter");
        pokemonCenter.setAddress("123 Oak Street");
        pokemonCenter = pokemonCenterDao.addCenter(pokemonCenter);

       //Create sample HealthRecord objects
        List<HealthRecord> expectedHealthRecords = new ArrayList<>();
        HealthRecord healthRecord1 = new HealthRecord();
        healthRecord1.setDescription("Recovered from a cold");
        healthRecord1.setDate(LocalDateTime.of(2023, 7, 20, 10, 0, 0));
        healthRecord1.setPokemon(pokemon);
        healthRecord1.setPokemonCenter(pokemonCenter);

        HealthRecord healthRecord2 = new HealthRecord();
        healthRecord2.setDescription("Checked for injuries");
        healthRecord2.setDate(LocalDateTime.of(2023, 7, 20, 12, 30, 0));
        healthRecord2.setPokemon(pokemon);
        healthRecord2.setPokemonCenter(pokemonCenter);

        expectedHealthRecords.add(healthRecord1);
        expectedHealthRecords.add(healthRecord2);

        // Add the HealthRecords to the database
        healthRecordDao.addHealthRecord(healthRecord1);
        healthRecordDao.addHealthRecord(healthRecord2);

        // Call the method to get all HealthRecords
        List<HealthRecord> actualHealthRecords = healthRecordDao.getAllHealthRecords();
        Assertions.assertEquals(expectedHealthRecords.size(), actualHealthRecords.size());
//        Assertions.assertTrue(actualHealthRecords.containsAll(expectedHealthRecords));
    }


        @Test
    public void testGetAllHealthRecords() {
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

        // Create sample PokemonCenter
        PokemonCenter pokemonCenter1 = new PokemonCenter();
        pokemonCenter1.setName("Pallet Town Pokecenter");
        pokemonCenter1.setAddress("123 Oak Street");
        pokemonCenter1 = pokemonCenterDao.addCenter(pokemonCenter1);

        PokemonCenter pokemonCenter2 = new PokemonCenter();
        pokemonCenter2.setName("Cerulean City Pokecenter");
        pokemonCenter2.setAddress("3355 Fully Cove");
        pokemonCenter2 = pokemonCenterDao.addCenter(pokemonCenter2);

        // Create sample HealthRecord objects
        List<HealthRecord> expectedHealthRecords = new ArrayList<>();
        HealthRecord healthRecord1 = new HealthRecord();
        healthRecord1.setDescription("Recovered from a cold");
        healthRecord1.setDate(LocalDateTime.of(2023, 7, 20, 10, 0, 0));
        healthRecord1.setPokemon(pokemon1);
        // Set PokemonCenter for healthRecord1
        healthRecord1.setPokemonCenter(pokemonCenter1);

        HealthRecord healthRecord2 = new HealthRecord();
        healthRecord2.setDescription("Checked for injuries");
        healthRecord2.setDate(LocalDateTime.of(2023, 7, 20, 12, 30, 0));
        healthRecord2.setPokemon(pokemon2);

        // Set PokemonCenter for healthRecord2
        healthRecord2.setPokemonCenter(pokemonCenter2);

        expectedHealthRecords.add(healthRecord1);
        expectedHealthRecords.add(healthRecord2);

        // Add the HealthRecords to the database
        healthRecordDao.addHealthRecord(healthRecord1);
        healthRecordDao.addHealthRecord(healthRecord2);

        // Call the method to get all HealthRecords
        List<HealthRecord> actualHealthRecords = healthRecordDao.getAllHealthRecords();

        // Verify that the expected and actual lists of HealthRecords are the same
        Assertions.assertEquals(expectedHealthRecords.size(), actualHealthRecords.size());
       // Assertions.assertTrue(actualHealthRecords.containsAll(expectedHealthRecords));
    }

    @Test
    public void testUpdateHealthRecord() {
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
        pokemon = pokemonDao.addPokemon(pokemon);

        // Create a sample PokemonCenter
        PokemonCenter pokemonCenter = new PokemonCenter();
        pokemonCenter.setName("Pallet Town Pokecenter");
        pokemonCenter.setAddress("123 Oak Street");
        pokemonCenter = pokemonCenterDao.addCenter(pokemonCenter);

        // Create a sample HealthRecord object to update
        HealthRecord healthRecordToUpdate = new HealthRecord();
        healthRecordToUpdate.setDescription("Recovered from a cold");
        healthRecordToUpdate.setDate(LocalDateTime.of(2023, 7, 20, 10, 0, 0));
        healthRecordToUpdate.setPokemon(pokemon);
        healthRecordToUpdate.setPokemonCenter(pokemonCenter);

        // Add the HealthRecord to the database
        HealthRecord addedHealthRecord = healthRecordDao.addHealthRecord(healthRecordToUpdate);

        // Retrieve the HealthRecord from the database using its ID
        HealthRecord retrievedHealthRecord = healthRecordDao.getHealthRecordById(addedHealthRecord.getId());

        // Assertions before the update
        Assertions.assertNotNull(retrievedHealthRecord);
        Assertions.assertEquals("Recovered from a cold", retrievedHealthRecord.getDescription());

        // Update the HealthRecord's description
        retrievedHealthRecord.setDescription("Feeling better now!");

        // Update the HealthRecord in the database
        healthRecordDao.updateHealthRecord(retrievedHealthRecord);

        // Retrieve the HealthRecord again after the update
        HealthRecord updatedHealthRecord = healthRecordDao.getHealthRecordById(addedHealthRecord.getId());

        // Assertions after the update
        Assertions.assertNotNull(updatedHealthRecord);
        Assertions.assertEquals("Feeling better now!", updatedHealthRecord.getDescription());
    }

    @Test
    public void testDeleteHealthRecordById() {
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
        pokemon = pokemonDao.addPokemon(pokemon);

        // Create a sample PokemonCenter
        PokemonCenter pokemonCenter = new PokemonCenter();
        pokemonCenter.setName("Pallet Town Pokecenter");
        pokemonCenter.setAddress("123 Oak Street");
        pokemonCenter = pokemonCenterDao.addCenter(pokemonCenter);

        // Create a sample HealthRecord object to delete
        HealthRecord healthRecordToDelete = new HealthRecord();
        healthRecordToDelete.setDescription("Recovered from a cold");
        healthRecordToDelete.setDate(LocalDateTime.of(2023, 7, 20, 10, 0, 0));
        healthRecordToDelete.setPokemon(pokemon);
        healthRecordToDelete.setPokemonCenter(pokemonCenter);

        // Add the HealthRecord to the database
        HealthRecord addedHealthRecord = healthRecordDao.addHealthRecord(healthRecordToDelete);

        // Call the method to delete the HealthRecord by ID
        healthRecordDao.deleteHealthRecordById(addedHealthRecord.getId());

        // Attempt to retrieve the HealthRecord after deletion
        HealthRecord deletedHealthRecord = healthRecordDao.getHealthRecordById(addedHealthRecord.getId());

        // Verify that the HealthRecord is not found (should be null)
        Assertions.assertNull(deletedHealthRecord);
    }

    @Test
    public void testGetAllHealthRecordsByPokemonCenter() {
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
        pokemon = pokemonDao.addPokemon(pokemon);

        // Create a sample PokemonCenter
        PokemonCenter pokemonCenter = new PokemonCenter();
        pokemonCenter.setName("Pallet Town Pokecenter");
        pokemonCenter.setAddress("123 Oak Street");
        pokemonCenter = pokemonCenterDao.addCenter(pokemonCenter);

        // Create sample HealthRecord objects
        List<HealthRecord> expectedHealthRecords = new ArrayList<>();
        HealthRecord healthRecord1 = new HealthRecord();
        healthRecord1.setDescription("Recovered from a cold");
        healthRecord1.setDate(LocalDateTime.of(2023, 7, 20, 10, 0, 0));
        healthRecord1.setPokemon(pokemon);
        healthRecord1.setPokemonCenter(pokemonCenter);

        HealthRecord healthRecord2 = new HealthRecord();
        healthRecord2.setDescription("Checked for injuries");
        healthRecord2.setDate(LocalDateTime.of(2023, 7, 20, 12, 30, 0));
        healthRecord2.setPokemon(pokemon);
        healthRecord2.setPokemonCenter(pokemonCenter);

        expectedHealthRecords.add(healthRecord1);
        expectedHealthRecords.add(healthRecord2);

        // Add the HealthRecords to the database
        healthRecordDao.addHealthRecord(healthRecord1);
        healthRecordDao.addHealthRecord(healthRecord2);

        // Call the method to get all HealthRecords for the PokemonCenter
        List<HealthRecord> actualHealthRecords = healthRecordDao.getAllHealthRecordsByPokemonCenter(pokemonCenter.getId());

        // Verify that the expected and actual lists of HealthRecords are the same
        Assertions.assertEquals(expectedHealthRecords.size(), actualHealthRecords.size());
        // Assertions.assertTrue(actualHealthRecords.containsAll(expectedHealthRecords));
    }

    }







