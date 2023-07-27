package com.fk.pokemoncare.dao;

import com.fk.pokemoncare.entities.HealthRecord;

import java.util.List;

public interface HealthRecordDao {

    HealthRecord getHealthRecordById(int id);

    List<HealthRecord> getAllHealthRecords();

    HealthRecord addHealthRecord(HealthRecord healthRecord);

    void updateHealthRecord(HealthRecord healthRecord);

    void deleteHealthRecordById(int id);
}
