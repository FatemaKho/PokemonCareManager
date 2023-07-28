package com.fk.pokemoncare.dao;
import com.fk.pokemoncare.entities.HealthRecord;
import com.fk.pokemoncare.entities.PokemonCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PokemonCenterDaoDB implements PokemonCenterDao {

    private static final String SELECT_CENTER_BY_ID = "SELECT * FROM pokemoncenter WHERE PokemonCenterID = ?";
    private static final String SELECT_ALL_CENTERS = "SELECT * FROM pokemoncenter";
    private static final String INSERT_CENTER = "INSERT INTO pokemoncenter (CenterName, CenterAddress) VALUES (?, ?)";
    private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";
    private static final String UPDATE_CENTER = "UPDATE pokemoncenter SET CenterName = ?, CenterAddress = ? WHERE PokemonCenterID = ?";
    private static final String DELETE_CENTER = "DELETE FROM pokemoncenter WHERE PokemonCenterID = ?";
    private static final String SELECT_HEALTHRECORDS_BY_CENTER = "SELECT * FROM healthrecord WHERE PokemonCenterID = ?";
    private static final String DELETE_HEALTH_RECORDS_BY_CENTER = "DELETE FROM healthrecord WHERE PokemonCenterID = ?";

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public PokemonCenter getCenterById(int id) {
        try {
        PokemonCenter center = jdbc.queryForObject(SELECT_CENTER_BY_ID, new PokemonCenterMapper(), id);
        center.setHealthrecords(getHealthRecordsByCenter(center));
        return center;
        } catch (
                DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<PokemonCenter> getAllCenters() {
        List<PokemonCenter> centers = jdbc.query(SELECT_ALL_CENTERS, new PokemonCenterMapper());
        for (PokemonCenter center : centers) {
            center.setHealthrecords(getHealthRecordsByCenter(center));
        }
        return centers;
    }

    @Override
    public PokemonCenter addCenter(PokemonCenter center) {
        jdbc.update(INSERT_CENTER, center.getName(), center.getAddress());
        int newCenterId = jdbc.queryForObject(SELECT_LAST_INSERT_ID, Integer.class);
        center.setId(newCenterId);
        return center;
    }

    @Override
    public void updateCenter(PokemonCenter center) {
        jdbc.update(UPDATE_CENTER, center.getName(), center.getAddress(), center.getId());
    }

    @Override
    public void deleteCenterById(int id) {
        // Delete associated health records first
        jdbc.update(DELETE_HEALTH_RECORDS_BY_CENTER, id);

        // Delete the Pokemon Center
        jdbc.update(DELETE_CENTER, id);
    }

    private List<HealthRecord> getHealthRecordsByCenter(PokemonCenter center) {
        List<HealthRecord> records= jdbc.query(SELECT_HEALTHRECORDS_BY_CENTER, new HealthRecordDaoDB.HealthRecordMapper(), center.getId());
        if(records.size()==0)
        {
            return null;
        }
        return records;
    }

    public static final class PokemonCenterMapper implements RowMapper<PokemonCenter> {
        @Override
        public PokemonCenter mapRow(ResultSet rs, int rowNum) throws SQLException {
            PokemonCenter center = new PokemonCenter();
            center.setId(rs.getInt("PokemonCenterID"));
            center.setName(rs.getString("CenterName"));
            center.setAddress(rs.getString("CenterAddress"));
            return center;
        }
    }



}

