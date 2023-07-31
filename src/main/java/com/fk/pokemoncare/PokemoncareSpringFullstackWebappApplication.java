package com.fk.pokemoncare;

import com.fk.pokemoncare.converters.StringToPokemonConverter;
import com.fk.pokemoncare.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PokemoncareSpringFullstackWebappApplication {

	@Autowired
	 ServiceInterface service;

	@Autowired
	public PokemoncareSpringFullstackWebappApplication(ServiceInterface service) {
		this.service = service;
	}

	public static void main(String[] args) {
		SpringApplication.run(PokemoncareSpringFullstackWebappApplication.class, args);
	}
}