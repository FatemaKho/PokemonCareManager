package com.fk.pokemoncare.dao;/*package com.fk.pokemoncare.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import com.fk.pokemoncare.dao.HealthRecordDaoDB;
import com.fk.pokemoncare.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HealthRecordDaoDBTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private HealthRecordDaoDB healthRecordDao;

    @InjectMocks
    PokemonDaoDB pokemonDao;

    @InjectMocks
    PokemonCenterDaoDB pokemonCenterDao;

    @InjectMocks
TrainerDaoDB trainerDao;

    @InjectMocks
    TypeDaoDB typeDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the objects with default values before each test method
        HealthRecord healthRecord = new HealthRecord();
        Pokemon pokemon = new Pokemon();
        PokemonCenter pokemonCenter = new PokemonCenter();
        Trainer trainer = new Trainer();
        // Set default values for each object as needed
    }



    @Test
    public void testGetHealthRecordById() {
        // Create a sample HealthRecord object
        HealthRecord expectedHealthRecord = new HealthRecord();
        expectedHealthRecord.setId(1);
        expectedHealthRecord.setDescription("Recovered from a cold");
        expectedHealthRecord.setDate(LocalDateTime.of(2023, 7, 20, 10, 0, 0));

        // Mock the queryForObject method of JdbcTemplate
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt()))
                .thenReturn(expectedHealthRecord);

        // Call the method to get the HealthRecord by ID
        HealthRecord actualHealthRecord = healthRecordDao.getHealthRecordById(1);

        // Verify that the expected and actual HealthRecord objects are the same
        assertEquals(expectedHealthRecord, actualHealthRecord);
    }

    @Test
    public void testGetAllHealthRecords() {
        // Create sample HealthRecord objects
        List<HealthRecord> expectedHealthRecords = new ArrayList<>();
        HealthRecord healthRecord1 = new HealthRecord();
        healthRecord1.setId(1);
        healthRecord1.setDescription("Recovered from a cold");
        healthRecord1.setDate(LocalDateTime.of(2023, 7, 20, 10, 0, 0));
        expectedHealthRecords.add(healthRecord1);

        HealthRecord healthRecord2 = new HealthRecord();
        healthRecord2.setId(2);
        healthRecord2.setDescription("Checked for injuries");
        healthRecord2.setDate(LocalDateTime.of(2023, 7, 20, 12, 30, 0));
        expectedHealthRecords.add(healthRecord2);

        // Mock the query method of JdbcTemplate
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(expectedHealthRecords);

        // Call the method to get all HealthRecords
        List<HealthRecord> actualHealthRecords = healthRecordDao.getAllHealthRecords();

        // Verify that the expected and actual lists of HealthRecords are the same
        assertEquals(expectedHealthRecords, actualHealthRecords);
    }

    @Test
    public void testAddHealthRecord() {
        // Create a sample HealthRecord object to add
        HealthRecord healthRecordToAdd = new HealthRecord();
        healthRecordToAdd.setDescription("Feeling great!");
        healthRecordToAdd.setDate(LocalDateTime.of(2023, 7, 20, 16, 45, 0));
        // Create a new Pokemon object and set its ID (assuming the ID is 1)
        Pokemon pokemon = new Pokemon();
        pokemon.setId(1);
        // Set the Pokemon for the HealthRecord
        healthRecordToAdd.setPokemon(pokemon);

        // Create a new PokemonCenter object and set its ID (assuming the ID is 1)
        PokemonCenter pokemonCenter = new PokemonCenter();
        pokemonCenter.setId(1);

        // Set the PokemonCenter for the HealthRecord
        healthRecordToAdd.setPokemonCenter(pokemonCenter);

        // Mock the queryForObject method of JdbcTemplate for LAST_INSERT_ID
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class)))
                .thenReturn(1); // Assuming the new HealthRecord ID is 1

        // Call the method to add the HealthRecord
        HealthRecord addedHealthRecord = healthRecordDao.addHealthRecord(healthRecordToAdd);

        // Verify that the addedHealthRecord has the correct ID (1 in this case)
        assertEquals(1, addedHealthRecord.getId());
    }


    @Test
    public void testUpdateHealthRecord() {
        //create Trainer
        Trainer trainer = new Trainer();
        trainer.setName("Ash Ketchum");
        trainer.setAge(11);
        trainer.setEmail("fkhondker123@gmail.com");
        trainer = trainerDao.addTrainer(trainer);


        // Create and set a Pokemon
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Pikachu");
        pokemon.setSpecies("Electric Mouse Pokemon");
        pokemon.setLevel(15);
        pokemon.setTrainer(trainer);
        pokemon = pokemonDao.addPokemon(pokemon);

        // Create and set a PokemonCenter
        PokemonCenter pokemonCenter = new PokemonCenter();
        pokemonCenter.setName("Pallet Town Pokecenter");
        pokemonCenter.setAddress("123 Oak Street");
        pokemonCenter = pokemonCenterDao.addCenter(pokemonCenter);

        // Create a HealthRecord and set all the fields
        HealthRecord healthRecord = new HealthRecord();
        healthRecord.setDescription("Recovered from a cold");
        healthRecord.setDate(LocalDateTime.of(2023, 7, 20, 10, 0));
        healthRecord.setPokemon(pokemon);
        healthRecord.setPokemonCenter(pokemonCenter);

        // Add the HealthRecord to the database
        HealthRecord addedHealthRecord = healthRecordDao.addHealthRecord(healthRecord);

        // Retrieve the HealthRecord from the database using its ID
        HealthRecord retrievedHealthRecord = healthRecordDao.getHealthRecordById(addedHealthRecord.getId());

        // Assertions
        Assertions.assertNotNull(retrievedHealthRecord);
        Assertions.assertEquals("Recovered from a cold", retrievedHealthRecord.getDescription());
        Assertions.assertEquals(LocalDateTime.of(2023, 7, 20, 10, 0), retrievedHealthRecord.getDate());
        Assertions.assertEquals("Pikachu", retrievedHealthRecord.getPokemon().getName());
        Assertions.assertEquals("Pallet Town Pokecenter", retrievedHealthRecord.getPokemonCenter().getName());
    }




    @Test
    public void testDeleteHealthRecordById() {
        // Call the method to delete the HealthRecord by ID
        healthRecordDao.deleteHealthRecordById(1);

        // Verify that the delete method was called with the correct parameter
        verify(jdbcTemplate).update(eq("DELETE FROM healthrecord WHERE HealthRecordID = ?"), eq(1));
    }
}
*/
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
@Transactional
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
        // Clear the database tables before each test method
        // Delete all health records from the database
        healthRecordDao.deleteAllHealthRecords();

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
        healthRecordToAdd.setDescription("Feeling great!");
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
        Assertions.assertTrue(actualHealthRecords.containsAll(expectedHealthRecords));
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
        Assertions.assertTrue(actualHealthRecords.containsAll(expectedHealthRecords));
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
}





