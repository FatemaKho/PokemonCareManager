package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class TrainerDaoDBTest {

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
    public void testAddTrainer() {
        // Create a sample Trainer
        Trainer trainer = new Trainer();
        trainer.setName("Ash Ketchum");
        trainer.setAge(11);
        trainer.setEmail("ash@example.com");

        // Save the Trainer to the database
        Trainer addedTrainer = trainerDao.addTrainer(trainer);

        // Verify that the addedTrainer has an ID assigned (not null)
        Assertions.assertNotNull(addedTrainer.getId());
    }

    @Test
    public void testGetTrainerById() {
        // Create a sample Trainer
        Trainer trainer = new Trainer();
        trainer.setName("Ash Ketchum");
        trainer.setAge(10);
        trainer.setEmail("ash@example.com");

        // Save the Trainer to the database
        Trainer addedTrainer = trainerDao.addTrainer(trainer);

        // Retrieve the Trainer from the database using its ID
        Trainer retrievedTrainer = trainerDao.getTrainerById(addedTrainer.getId());

        // Assertions to check if the retrieved Trainer is the same as the added one
        Assertions.assertNotNull(retrievedTrainer);
        Assertions.assertEquals("Ash Ketchum", retrievedTrainer.getName());
        Assertions.assertEquals(10, retrievedTrainer.getAge());
        Assertions.assertEquals("ash@example.com", retrievedTrainer.getEmail());
    }

    @Test
    public void testGetAllTrainers() {
        // Create sample Trainers
        Trainer trainer1 = new Trainer();
        trainer1.setName("Ash Ketchum");
        trainer1.setAge(10);
        trainer1.setEmail("ash@example.com");
        trainerDao.addTrainer(trainer1);

        Trainer trainer2 = new Trainer();
        trainer2.setName("Misty");
        trainer2.setAge(12);
        trainer2.setEmail("misty@example.com");
        trainerDao.addTrainer(trainer2);

        // Call the method to get all Trainers
        List<Trainer> allTrainers = trainerDao.getAllTrainers();

        // Verify that the expected and actual lists of Trainers are the same
        Assertions.assertEquals(2, allTrainers.size());
        Assertions.assertTrue(allTrainers.contains(trainer1));
        Assertions.assertTrue(allTrainers.contains(trainer2));
    }

    @Test
    public void testUpdateTrainer() {
        // Create a sample Trainer
        Trainer trainer = new Trainer();
        trainer.setName("Ash Ketchum");
        trainer.setAge(10);
        trainer.setEmail("ash@example.com");

        // Save the Trainer to the database
        Trainer addedTrainer = trainerDao.addTrainer(trainer);

        // Retrieve the Trainer from the database using its ID
        Trainer retrievedTrainer = trainerDao.getTrainerById(addedTrainer.getId());

        // Assertions before the update
        Assertions.assertNotNull(retrievedTrainer);
        Assertions.assertEquals(10, retrievedTrainer.getAge());

        // Update the Trainer's age
        retrievedTrainer.setAge(15);

        // Update the Trainer in the database
        trainerDao.updateTrainer(retrievedTrainer);

        // Retrieve the Trainer again after the update
        Trainer updatedTrainer = trainerDao.getTrainerById(addedTrainer.getId());

        // Assertions after the update
        Assertions.assertNotNull(updatedTrainer);
        Assertions.assertEquals(15, updatedTrainer.getAge());
    }

    @Test
    public void testDeleteTrainer() {
        // Create a sample Trainer
        Trainer trainer = new Trainer();
        trainer.setName("Ash Ketchum");
        trainer.setAge(10);
        trainer.setEmail("ash@example.com");

        // Save the Trainer to the database
        Trainer addedTrainer = trainerDao.addTrainer(trainer);

        // Call the method to delete the Trainer by ID
        trainerDao.deleteTrainer(addedTrainer.getId());

        // Attempt to retrieve the Trainer after deletion
        Trainer deletedTrainer = trainerDao.getTrainerById(addedTrainer.getId());

        // The deleted Trainer should be null
        Assertions.assertNull(deletedTrainer);
    }

}