package com.fk.pokemoncare.controller;// ... (other imports)

import com.fk.pokemoncare.entities.HealthRecord;
import com.fk.pokemoncare.entities.PokemonCenter;
import com.fk.pokemoncare.service.DuplicateNameExistsException;
import com.fk.pokemoncare.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class PokemonCenterController {

    @Autowired
    ServiceInterface service;

    // Display all Pokemon Centers
    @GetMapping("/pokemonCenters")
    public String displayPokemonCenters(Model model) {
        List<PokemonCenter> pokemonCenters = service.getAllCenters();
        model.addAttribute("pokemonCenters", pokemonCenters);
        return "pokemonCenters";
    }

    // Add a new Pokemon Center - Show the form
    @GetMapping("/addPokemonCenter")
    public String showAddPokemonCenterForm(Model model) {
        model.addAttribute("pokemonCenter", new PokemonCenter());
        return "addPokemonCenter";
    }

    // Add a new Pokemon Center - Process the form submission
    @PostMapping("/addPokemonCenter")
    public String addPokemonCenter(@ModelAttribute("pokemonCenter") @Valid PokemonCenter pokemonCenter,
                                   BindingResult result,
                                   Model model, HttpServletRequest request) {

        if("".equals(pokemonCenter.getName())) {
            FieldError error = new FieldError("pokemonCenter", "name", "Pokemon Center needs name");
            result.addError(error);
        }
        if("".equals(pokemonCenter.getAddress())){
            FieldError error = new FieldError("pokemonCenter", "address", "Pokemon Center needs address");
            result.addError(error);
        }

        try {
           if(!result.hasErrors()) {
               service.addCenter(pokemonCenter);
           }
        } catch (DuplicateNameExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "addPokemonCenter";
        }

        if (result.hasErrors()) {
            return "addPokemonCenter";
        }


        return "redirect:/pokemonCenters";
    }


    // Show details of a specific Pokemon Center
    @GetMapping("/detailPokemonCenter")
    public String showPokemonCenterDetail(@RequestParam("id") int id, Model model) {
        PokemonCenter pokemonCenter = service.getCenterById(id);
        List<HealthRecord> healthRecords = service.getAllHealthRecordsByPokemonCenter(id);
        pokemonCenter.setHealthrecords(healthRecords);
        model.addAttribute("pokemonCenter", pokemonCenter);
        model.addAttribute("healthRecords", healthRecords);
        return "detailPokemonCenter";
    }

    // Edit a Pokemon Center - Show the form
    @GetMapping("/editPokemonCenter")
    public String showEditPokemonCenterForm(@RequestParam("id") int id, Model model) {
        PokemonCenter pokemonCenter = service.getCenterById(id);
        model.addAttribute("pokemonCenter", pokemonCenter);
        return "editPokemonCenter";
    }

    // Edit a Pokemon Center - Process the form submission
    @PostMapping("/editPokemonCenter")
    public String editPokemonCenter(@ModelAttribute("pokemonCenter") PokemonCenter pokemonCenter,
                                    Model model) throws DuplicateNameExistsException {
        service.updateCenter(pokemonCenter);
        return "redirect:/pokemonCenters";
    }

    // Delete a Pokemon Center
    @PostMapping("/deletePokemonCenter")
    public String deletePokemonCenter(@RequestParam("id") int id) {
        service.deleteCenterById(id);
        return "redirect:/pokemonCenters";
    }
}

