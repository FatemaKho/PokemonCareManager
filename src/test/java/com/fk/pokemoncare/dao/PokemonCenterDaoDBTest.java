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
public class PokemonCenterDaoDBTest {
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
    public void testAddAndGetCenter() {
        // Create a sample PokemonCenter
        PokemonCenter center = new PokemonCenter();
        center.setName("Pallet Town Pokecenter");
        center.setAddress("123 Oak Street");

        // Add the PokemonCenter to the database
        pokemonCenterDao.addCenter(center);

        // Retrieve the PokemonCenter by ID from the database
        PokemonCenter retrievedCenter = pokemonCenterDao.getCenterById(center.getId());

        // Assertions
        Assertions.assertNotNull(retrievedCenter);
        Assertions.assertEquals(center.getId(), retrievedCenter.getId());
        Assertions.assertEquals(center.getName(), retrievedCenter.getName());
        Assertions.assertEquals(center.getAddress(), retrievedCenter.getAddress());
        Assertions.assertNull(retrievedCenter.getHealthrecords());
    }

    @Test
    public void testGetAllCenters() {
        // Create sample PokemonCenters
        PokemonCenter center1 = new PokemonCenter();
        center1.setName("Pallet Town Pokecenter");
        center1.setAddress("123 Oak Street");

        PokemonCenter center2 = new PokemonCenter();
        center2.setName("Cerulean City Pokecenter");
        center2.setAddress("3355 Fully Cove");

        // Add the PokemonCenters to the database
        pokemonCenterDao.addCenter(center1);
        pokemonCenterDao.addCenter(center2);

        // Call the method to get all PokemonCenters
        List<PokemonCenter> actualCenters = pokemonCenterDao.getAllCenters();

        // Assertions
        Assertions.assertEquals(2, actualCenters.size());
        Assertions.assertTrue(actualCenters.contains(center1));
        Assertions.assertTrue(actualCenters.contains(center2));
    }

    @Test
    public void testUpdateCenter() {
        // Create a sample PokemonCenter
        PokemonCenter center = new PokemonCenter();
        center.setName("Pallet Town Pokecenter");
        center.setAddress("123 Oak Street");

        // Add the PokemonCenter to the database
        pokemonCenterDao.addCenter(center);

        // Update the PokemonCenter's name and address
        center.setName("Updated Pokecenter");
        center.setAddress("456 Maple Ave");

        // Update the PokemonCenter in the database
        pokemonCenterDao.updateCenter(center);

        // Retrieve the PokemonCenter again after the update
        PokemonCenter updatedCenter = pokemonCenterDao.getCenterById(center.getId());

        // Assertions
        Assertions.assertNotNull(updatedCenter);
        Assertions.assertEquals("Updated Pokecenter", updatedCenter.getName());
        Assertions.assertEquals("456 Maple Ave", updatedCenter.getAddress());
    }

    @Test
    public void testDeleteCenterById() {
        // Create a sample PokemonCenter
        PokemonCenter center = new PokemonCenter();
        center.setName("Pallet Town Pokecenter");
        center.setAddress("123 Oak Street");

        // Add the PokemonCenter to the database
        pokemonCenterDao.addCenter(center);

        // Call the method to delete the PokemonCenter by ID
        pokemonCenterDao.deleteCenterById(center.getId());

        // Attempt to retrieve the PokemonCenter after deletion
        PokemonCenter deletedCenter = pokemonCenterDao.getCenterById(center.getId());

        // Verify that the PokemonCenter is not found (should be null)
        Assertions.assertNull(deletedCenter);
    }
}
