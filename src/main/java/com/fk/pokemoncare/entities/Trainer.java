package com.fk.pokemoncare.entities;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

public class Trainer {
    private int id;

    @NotBlank(message = "Name must not be empty")
    private String name;
    @NotBlank(message = "Age must not be empty")
    private int age;
    @NotBlank(message = "Email must not be empty")
    private String email;
    private List<Pokemon> pokemons;

    public Trainer() {

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        if (!(o instanceof Trainer)) return false;
        Trainer trainer = (Trainer) o;
        return id == trainer.id && age == trainer.age && Objects.equals(name, trainer.name) && Objects.equals(email, trainer.email) && Objects.equals(pokemons, trainer.pokemons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email, pokemons);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", pokemons=" + pokemons +
                '}';
    }
}