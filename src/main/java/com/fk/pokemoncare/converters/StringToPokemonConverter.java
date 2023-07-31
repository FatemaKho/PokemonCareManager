package com.fk.pokemoncare.converters;

import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.service.ServiceInterface;
import org.springframework.core.convert.converter.Converter;
import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPokemonConverter implements Converter<String, Pokemon> {

    private final ServiceInterface service;

    @Autowired
    public StringToPokemonConverter(ServiceInterface service) {
        this.service = service;
    }

    @Override
    public Pokemon convert(String pokemonId) {
        int id = Integer.parseInt(pokemonId);
        return service.getPokemonById(id);
    }
}
