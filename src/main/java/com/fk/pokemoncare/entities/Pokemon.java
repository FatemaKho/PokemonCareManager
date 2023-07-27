package com.fk.pokemoncare.entities;

import java.util.List;
import java.util.Objects;

public class Pokemon {
    private int id;
    private String name;
    private String species;
    private int level;
    private Trainer trainer;
    private List<Type> types;

    public Pokemon() {
    }

    public Pokemon(int id, String name, String species, int level, Trainer trainer, List<Type> types) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.level = level;
        this.trainer = trainer;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pokemon)) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id && level == pokemon.level && Objects.equals(name, pokemon.name) && Objects.equals(species, pokemon.species) && Objects.equals(trainer, pokemon.trainer) && Objects.equals(types, pokemon.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, species, level, trainer, types);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", level=" + level +
                ", trainer=" + trainer +
                ", types=" + types +
                '}';
    }
}
