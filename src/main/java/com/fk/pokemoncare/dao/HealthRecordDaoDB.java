package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.entities.HealthRecord;
import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.PokemonCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class HealthRecordDaoDB implements HealthRecordDao {

    private static final String SELECT_HEALTH_RECORD_BY_ID = "SELECT * FROM healthrecord WHERE HealthRecordID = ?";
    private static final String SELECT_ALL_HEALTH_RECORDS = "SELECT * FROM healthrecord";
    private static final String INSERT_HEALTH_RECORD = "INSERT INTO healthrecord (HealthDescription, HealthDateRecorded, PokemonID, PokemonCenterID) VALUES (?, ?, ?, ?)";
    private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    private static final String UPDATE_HEALTH_RECORD = "UPDATE healthrecord SET HealthDescription = ?, HealthDateRecorded = ?, PokemonID = ?, PokemonCenterID = ? WHERE HealthRecordID = ?";
    private static final String DELETE_HEALTH_RECORD = "DELETE FROM healthrecord WHERE HealthRecordID = ?";
    private static final String SELECT_POKEMON_FOR_HEALTH_RECORD = "SELECT p.* FROM pokemon p " +
            "INNER JOIN healthrecord h ON p.PokemonID = h.PokemonID " +
            "WHERE h.HealthRecordID = ?";
    private static final String SELECT_POKEMON_CENTER_FOR_HEALTH_RECORD = "SELECT pc.* FROM pokemoncenter pc " +
            "INNER JOIN healthrecord h ON pc.PokemonCenterID = h.PokemonCenterID " +
            "WHERE h.HealthRecordID = ?";
    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    PokemonDaoDB pokemonDao;

    @Override
    public HealthRecord getHealthRecordById(int id) {
    try {
        HealthRecord healthRecord = jdbc.queryForObject(SELECT_HEALTH_RECORD_BY_ID, new HealthRecordMapper(), id);
        setRelatedObjects(healthRecord);
        return healthRecord;
    } catch (
    DataAccessException ex) {
        return null;
    }
}

    @Override
    public List<HealthRecord> getAllHealthRecords() {
        try {
            List<HealthRecord> healthRecords = jdbc.query(SELECT_ALL_HEALTH_RECORDS, new HealthRecordMapper());
            for (HealthRecord healthRecord : healthRecords) {
                setRelatedObjects(healthRecord);
            }
            return healthRecords;
        } catch (DataAccessException ex) {
            // Handle the exception or rethrow it
            return null;
        }
    }


    @Override
    @Transactional
    public HealthRecord addHealthRecord(HealthRecord healthRecord) {
        jdbc.update(
                INSERT_HEALTH_RECORD,
                healthRecord.getDescription(),
                healthRecord.getDate(),
                healthRecord.getPokemon().getId(),
                healthRecord.getPokemonCenter().getId()
        );
        int newHealthRecordId = jdbc.queryForObject(SELECT_LAST_INSERT_ID, Integer.class);
        healthRecord.setId(newHealthRecordId);
        return healthRecord;
    }

    @Override
    public void updateHealthRecord(HealthRecord healthRecord) {
        jdbc.update(
                UPDATE_HEALTH_RECORD,
                healthRecord.getDescription(),
                healthRecord.getDate(),
                healthRecord.getPokemon().getId(),
                healthRecord.getPokemonCenter().getId(),
                healthRecord.getId()
        );
    }

    @Override
    public void deleteHealthRecordById(int id) {
        jdbc.update(DELETE_HEALTH_RECORD, id);
    }

    private void setRelatedObjects(HealthRecord healthRecord) {
        healthRecord.setPokemon(getPokemonForHealthRecord(healthRecord.getId()));
        healthRecord.setPokemonCenter(getPokemonCenterForHealthRecord(healthRecord.getId()));
    }

    private Pokemon getPokemonForHealthRecord(int healthRecordId) {
        try {
            Pokemon pokemon= jdbc.queryForObject(SELECT_POKEMON_FOR_HEALTH_RECORD, new PokemonDaoDB.PokemonMapper(), healthRecordId);
            pokemon=pokemonDao.getPokemonById(pokemon.getId());
            return pokemon;
        } catch (Exception ex) {
            return null;
        }
    }

    private PokemonCenter getPokemonCenterForHealthRecord(int healthRecordId) {
        try {
            return jdbc.queryForObject(SELECT_POKEMON_CENTER_FOR_HEALTH_RECORD, new PokemonCenterDaoDB.PokemonCenterMapper(), healthRecordId);
        } catch (Exception ex) {
            return null;
        }
    }

    public void deleteAllHealthRecords() {
        String sql = "DELETE FROM healthrecord";
        jdbc.update(sql);
    }
    public List<HealthRecord> getAllHealthRecordsByPokemonCenter(int pokemonCenterId) {
        try {
            String sql = "SELECT * FROM healthrecord WHERE PokemonCenterID = ?";
            List<HealthRecord> healthRecords = jdbc.query(sql, new HealthRecordMapper(), pokemonCenterId);
            for (HealthRecord healthRecord : healthRecords) {
                setRelatedObjects(healthRecord);
            }
            return healthRecords;
        } catch (DataAccessException ex) {
            // Handle the exception or rethrow it
            return null;
        }

    }
    @Override
    public List<HealthRecord> getHealthRecordsByDate(LocalDateTime date) {
        String sql = "SELECT * FROM healthrecord WHERE DATE(HealthDateRecorded) = ?";
        List<HealthRecord> healthRecords = jdbc.query(sql, new HealthRecordMapper(), date);
        for (HealthRecord healthRecord : healthRecords) {
            setRelatedObjects(healthRecord);
        }
        return healthRecords;
    }
    public static final class HealthRecordMapper implements RowMapper<HealthRecord> {
        @Override
        public HealthRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            HealthRecord healthRecord = new HealthRecord();
            healthRecord.setDescription(rs.getString("HealthDescription"));
            healthRecord.setDate(rs.getTimestamp("HealthDateRecorded").toLocalDateTime());
            healthRecord.setId(rs.getInt("HealthRecordID"));
            return healthRecord;
        }

    }
    }




