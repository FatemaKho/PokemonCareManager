package com.fk.pokemoncare.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.Trainer;
import com.fk.pokemoncare.entities.Type;
import com.fk.pokemoncare.service.DuplicateNameExistsException;
import com.fk.pokemoncare.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class PokemonController {

    @Autowired
    ServiceInterface service;

    @GetMapping("/pokemons")
    public String displayAllPokemons(Model model) {
        List<Pokemon> allPokemons = service.getAllPokemon();
        model.addAttribute("pokemons", allPokemons);
        return "pokemons";
    }

    @GetMapping("/addPokemon")
    public String displayAddPokemonForm(Model model) {
        Pokemon pokemon = new Pokemon();
        List<Type> allTypes = service.getAllTypes();
        List<Trainer> trainers = service.getAllTrainers();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("types", allTypes);
        model.addAttribute("trainers", trainers);
        return "addPokemon";
    }

    @PostMapping("/addPokemon")
    public String addPokemon(@ModelAttribute("pokemon") @Valid Pokemon pokemon,
                             @RequestParam("types") List<Integer> selectedTypeIds,
                             @RequestParam("trainers") int selectedTrainerId,
                             Model model) throws DuplicateNameExistsException {
        List<Type> selectedTypes = selectedTypeIds.stream()
                .map(typeId -> service.getTypeByID(typeId))
                .collect(Collectors.toList());

        pokemon.setTypes(selectedTypes);

        // Fetch the Trainer object based on the Trainer ID
        Trainer trainer = service.getTrainerById(selectedTrainerId);

        // Set the Trainer object in the Pokemon
        pokemon.setTrainer(trainer);

        Pokemon addedPokemon = service.addPokemon(pokemon);
        model.addAttribute("pokemon", addedPokemon);
        return "redirect:/pokemons";
    }

    @GetMapping("/detailPokemon")
    public String showPokemonDetail(@RequestParam("id") int id, Model model) {
        Pokemon pokemon = service.getPokemonById(id);
        model.addAttribute("pokemon", pokemon);
        return "detailPokemon";
    }

    @GetMapping("/editPokemon")
    public String showEditPokemonForm(@RequestParam("id") int id, Model model) {
        Pokemon pokemon = service.getPokemonById(id);
        List<Type> allTypes = service.getAllTypes();
        List<Trainer> trainers= service.getAllTrainers();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("types", allTypes);
        model.addAttribute("trainers", trainers);

        return "editPokemon";
    }

    @PostMapping("/editPokemon")
    public String updatePokemon(HttpServletRequest request, @ModelAttribute("pokemon") @Valid Pokemon pokemon,
                                BindingResult bindingResult,
                                @RequestParam("types") List<Integer> selectedTypeIds,
                                @RequestParam("trainers") int selectedTrainerId,
                                Model model) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            List<Type> allTypes = service.getAllTypes();
            List<Trainer> trainers = service.getAllTrainers(); // Add this line to fetch all trainers
            model.addAttribute("types", allTypes);
            model.addAttribute("trainers", trainers); // Add trainers to the model
            return "editPokemon";
        }

        List<Type> selectedTypes = new ArrayList<>();
        for (Integer typeId : selectedTypeIds) {
            Type type = service.getTypeByID(typeId);
            selectedTypes.add(type);
        }
        pokemon.setTypes(selectedTypes);

        // Fetch the Trainer object based on the Trainer ID
        Trainer trainer = service.getTrainerById(selectedTrainerId);
        // Set the Trainer object in the Pokemon
        pokemon.setTrainer(trainer);

        // Update each Type's Pokemons attribute
        for (Type type : selectedTypes) {
            List<Pokemon> typePokemons = service.getPokemonByTypeId(type);
            typePokemons.add(pokemon);
            type.setPokemons(typePokemons);
        }

        // Now update the Pokemon
        service.updatePokemon(pokemon);
        return "redirect:/pokemons";
    }


    @PostMapping("/deletePokemon")
    public String deletePokemon(HttpServletRequest request, @RequestParam("id") int id) {
        service.deletePokemonById(id);
        return "redirect:/pokemons";
    }

    // Add this method to handle type conversion for the "types" parameter
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "types", new CustomCollectionEditor(List.class) {
            protected Object convertElement(Object element) {
                return service.getTypeByID(Integer.parseInt((String) element));
            }
        });
    }
}


