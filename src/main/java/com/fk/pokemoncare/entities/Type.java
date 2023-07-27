package com.fk.pokemoncare.entities;

import java.util.List;
import java.util.Objects;

public class Type {
    private int id;
    private String name;
    private List<Pokemon> pokemons;

    public Type() {
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

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Type)) return false;
        Type type = (Type) o;
        return id == type.id && Objects.equals(name, type.name) && Objects.equals(pokemons, type.pokemons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pokemons);
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pokemons=" + pokemons +
                '}';
    }
}
