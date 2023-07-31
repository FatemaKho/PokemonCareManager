package com.fk.pokemoncare.controller;

import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.Type;
import com.fk.pokemoncare.service.DuplicateNameExistsException;
import com.fk.pokemoncare.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PokemonTypeController {

    @Autowired
    ServiceInterface service;

    // Display all Pokemon types
    @GetMapping("/pokemonTypes")
    public String displayPokemonTypes(Model model) {
        List<Type> pokemonTypes = service.getAllTypes();

        // Fetch Pokemons for each PokemonType and set them
        for (Type pokemonType : pokemonTypes) {
            List<Pokemon> pokemons = service.getPokemonByTypeId(pokemonType);
            pokemonType.setPokemons(pokemons);
        }

        model.addAttribute("pokemonTypes", pokemonTypes);

        return "pokemonTypes";
    }

    @GetMapping("/detailPokemonType")
    public String showPokemonTypeDetail(@RequestParam("id") int id, Model model) {
        Type pokemonType = service.getTypeByID(id);
        List<Pokemon> pokemons = service.getPokemonByTypeId(pokemonType);
        model.addAttribute("pokemonType", pokemonType);
        model.addAttribute("pokemons", pokemons);
        return "detailPokemonType";
    }
    // Add a new Pokemon type - Show the form
    @GetMapping("/addPokemonType")
    public String showAddPokemonTypeForm(Model model) {
        model.addAttribute("pokemonType", new Type());
        return "addPokemonType";
    }

    // Add a new Pokemon type - Process the form submission
    @PostMapping("/addPokemonType")
    public String addPokemonType(@ModelAttribute("pokemonType") @Valid Type pokemonType,
                                 BindingResult result,
                                 Model model) {
        try {
            service.validateType(pokemonType);
        } catch (DuplicateNameExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "addPokemonType";
        }

        service.addType(pokemonType);
        return "redirect:/pokemonTypes";
    }

    // Edit a Pokemon type - Show the form
    @GetMapping("/editPokemonType")
    public String showEditPokemonTypeForm(@RequestParam("id") int id, Model model) {
        Type pokemonType = service.getTypeByID(id);
        model.addAttribute("pokemonType", pokemonType);
        return "editPokemonType";
    }

    // Edit a Pokemon type - Process the form submission
    @PostMapping("/editPokemonType")
    public String editPokemonType(@ModelAttribute("pokemonType") @Valid Type pokemonType,
                                  BindingResult result,
                                  Model model) throws DuplicateNameExistsException {
        try {
            service.validateType(pokemonType);
        } catch (DuplicateNameExistsException e) {
            throw new RuntimeException(e);
        }

        service.updateType(pokemonType);
        return "redirect:/pokemonTypes";
    }

    // Delete a Pokemon type
    @PostMapping("/deletePokemonType")
    public String deletePokemonType(@RequestParam("id") int id) {
        service.deleteTypeByID(id);
        return "redirect:/pokemonTypes";
    }
}
