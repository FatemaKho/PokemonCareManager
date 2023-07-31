package com.fk.pokemoncare.entities;



import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;




public class HealthRecord {
    private int id;

    @NotBlank(message = "Description must not be empty")
    private String description;

    private LocalDateTime date;
    private Pokemon pokemon;
    private PokemonCenter pokemonCenter;


    public HealthRecord(int id, String description, LocalDateTime date, Pokemon pokemon, PokemonCenter pokemonCenter) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.pokemon = pokemon;
        this.pokemonCenter = pokemonCenter;
    }

    public HealthRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public PokemonCenter getPokemonCenter() {
        return pokemonCenter;
    }

    public void setPokemonCenter(PokemonCenter pokemonCenter) {
        this.pokemonCenter = pokemonCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HealthRecord)) return false;
        HealthRecord that = (HealthRecord) o;
        return id == that.id && Objects.equals(description, that.description) && Objects.equals(date, that.date) && Objects.equals(pokemon, that.pokemon) && Objects.equals(pokemonCenter, that.pokemonCenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, date, pokemon, pokemonCenter);
    }

    @Override
    public String toString() {
        return "HealthRecord{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", pokemon=" + pokemon +
                ", pokemonCenter=" + pokemonCenter +
                '}';
    }
}

