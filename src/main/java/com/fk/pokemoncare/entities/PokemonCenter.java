package com.fk.pokemoncare.entities;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

public class PokemonCenter {
    private int id;
    @NotBlank(message = "Name not be empty")
    private String name;
   @NotBlank(message = "Address not be empty")
    private String address;
   @NotBlank(message = "Must not be empty")
    private List<HealthRecord> healthrecords;

    public PokemonCenter() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<HealthRecord> getHealthrecords() {
        return healthrecords;
    }

    public void setHealthrecords(List<HealthRecord> healthrecords) {
        this.healthrecords = healthrecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokemonCenter)) return false;
        PokemonCenter that = (PokemonCenter) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(healthrecords, that.healthrecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, healthrecords);
    }

    @Override
    public String toString() {
        return "PokemonCenter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", healthrecords=" + healthrecords +
                '}';
    }
}