package com.fk.pokemoncare.controller;

import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.Trainer;
import com.fk.pokemoncare.service.DuplicateEmailExistsException;
import com.fk.pokemoncare.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TrainerController {

    @Autowired
    ServiceInterface service;

    @GetMapping("/trainers")
    public String displayAllTrainers(Model model) {
        List<Trainer> trainers = service.getAllTrainers();
        List<Pokemon> allPokemons = service.getAllPokemon(); // Fetch all available Pokemons
        model.addAttribute("pokemons", allPokemons); // Add the list of pokemons to the model
        for (Trainer trainer : trainers) {
            List<Pokemon> pokemons = service.getPokemonByTrainerId(trainer);
            trainer.setPokemons(pokemons);
        }
        model.addAttribute("trainers", trainers);
        return "trainers";
    }

    @GetMapping("/addTrainer")
    public String displayAddTrainerForm(Model model) {
        Trainer trainer = new Trainer();
        List<Pokemon> pokemons =service.getAllPokemon();
        model.addAttribute("trainer", trainer);
        model.addAttribute("pokemons", pokemons);
        return "addTrainer";
    }

    @PostMapping("/addTrainer")
    public String addTrainer(HttpServletRequest request, @ModelAttribute("trainer") @Valid Trainer trainer,
                             @RequestParam("pokemons") List<Integer> selectedPokemonIds, Model model) {
        List<Pokemon> selectedPokemons = new ArrayList<>();
        for (Integer pokemonId : selectedPokemonIds) {
            Pokemon pokemon = service.getPokemonById(pokemonId);
            selectedPokemons.add(pokemon);
        }
        trainer.setPokemons(selectedPokemons);

        try {
            Trainer addedTrainer = service.addTrainer(trainer);
            model.addAttribute("trainer", addedTrainer);
            return "redirect:/trainers";
        } catch (DuplicateEmailExistsException e) {
            model.addAttribute("error", "Trainer with the same email already exists.");
            List<Trainer> trainers = service.getAllTrainers();
            model.addAttribute("trainers", trainers);
            return "addTrainer";
        }
    }

    @GetMapping("/detailTrainer")
    public String showTrainerDetail(@RequestParam("id") int id, Model model) {
        Trainer trainer = service.getTrainerById(id);
        List<Pokemon> pokemons = service.getPokemonByTrainerId(trainer);
        trainer.setPokemons(pokemons);
        model.addAttribute("trainer", trainer);
        return "detailTrainer";
    }


    @GetMapping("/editTrainer")
    public String showEditTrainerForm(@RequestParam("id") int id, Model model) {
        Trainer trainer = service.getTrainerById(id);
        List<Pokemon> allPokemons = service.getAllPokemon();// Fetch all available Pokemons
        model.addAttribute("trainer", trainer);
        model.addAttribute("pokemons", allPokemons);
        return "editTrainer";
    }

    /*@PostMapping("/editTrainer")
    public String updateTrainer(HttpServletRequest request, @ModelAttribute("trainer") @Valid Trainer trainer) {
        service.updateTrainer(trainer);
        return "redirect:/trainers";
    }
*/
    @PostMapping("/editTrainer")
    public String updateTrainer(HttpServletRequest request, @ModelAttribute("trainer") @Valid Trainer trainer, @RequestParam("pokemons") List<Integer> selectedPokemonIds) {
        List<Pokemon> selectedPokemons = new ArrayList<>();
        for (Integer pokemonId : selectedPokemonIds) {
            Pokemon pokemon = service.getPokemonById(pokemonId);
            selectedPokemons.add(pokemon);
        }
        trainer.setPokemons(selectedPokemons);

        // Update each Pokemon's TrainerID attribute
        for (Pokemon pokemon : selectedPokemons) {
            pokemon.setTrainer(trainer);
        }

        // Now update the trainer
        service.updateTrainer(trainer);
        return "redirect:/trainers";
    }
    @PostMapping("/deleteTrainer")
    public String deleteTrainer(HttpServletRequest request, @RequestParam("id") int id) {
        service.deleteTrainer(id);
        return "redirect:/trainers";
    }
}


