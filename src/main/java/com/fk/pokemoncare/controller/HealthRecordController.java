package com.fk.pokemoncare.controller;

import com.fk.pokemoncare.entities.HealthRecord;
import com.fk.pokemoncare.entities.Pokemon;
import com.fk.pokemoncare.entities.PokemonCenter;
import com.fk.pokemoncare.service.ServiceInterface;
//import javax.validation.Valid;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

@Controller
public class HealthRecordController {

    @Autowired
    ServiceInterface service;

    // Display all health records
    @GetMapping("/healthRecords")
    public String displayHealthRecords(Model model) {
        List<HealthRecord> healthRecords = service.getAllHealthRecords();
        model.addAttribute("healthRecords", healthRecords);
        List<PokemonCenter> pokemonCenters = service.getAllCenters();
        model.addAttribute("pokemonCenters", pokemonCenters);
        return "healthRecords";
    }

    // Add a new health record - Show the form
    //take out the pokemon type and trainer column, we're showing that in details.
    //form should have drop down for pokemon, pokecenters, and fill in for description
    // and a date time picker for date..
    @GetMapping("/addHealthRecord")
    public String showAddHealthRecordForm(Model model) {
        List<Pokemon> pokemons = service.getAllPokemon();
        List<PokemonCenter> pokemonCenters = service.getAllCenters();
        model.addAttribute("pokemons", pokemons);
        model.addAttribute("pokemonCenters", pokemonCenters);
        model.addAttribute("healthRecord", new HealthRecord());
        return "addHealthRecord";

    }

    @GetMapping("healthRecordsByDate")
    public String getHealthRecordsByDate(HttpServletRequest request,
                                         Model model) {
        String datetime = request.getParameter("date");

        // Perform manual validation if needed
        if (datetime == null || datetime.isEmpty()) {
            model.addAttribute("error", "Date is required.");
            List<HealthRecord> healthRecords = service.getAllHealthRecords();
            model.addAttribute("healthRecords", healthRecords);
            return "healthRecords"; // or any other view to display the health records with the error message
        }

        LocalDate healthRecordDate;
        try {
            healthRecordDate = LocalDate.parse(datetime);

            // Validate if the date is in the future
            LocalDate currentDate = LocalDate.now();
            if (healthRecordDate.isAfter(currentDate)) {
                model.addAttribute("error", "Date cannot be in the future.");
                List<HealthRecord> healthRecords = service.getAllHealthRecords();
                model.addAttribute("healthRecords", healthRecords);
                return "healthRecords"; // or any other view to display the health records with the error message
            }

        } catch (DateTimeParseException e) {
            model.addAttribute("error", "Invalid date format.");
            List<HealthRecord> healthRecords = service.getAllHealthRecords();
            model.addAttribute("healthRecords", healthRecords);
            return "healthRecords"; // or any other view to display the health records with the error message
        }

        LocalDateTime healthRecordDateTime = healthRecordDate.atStartOfDay();
        List<HealthRecord> healthRecords = service.getHealthRecordsByDate(healthRecordDateTime);
        model.addAttribute("healthRecords", healthRecords);
        return "healthRecords";
    }








    @GetMapping("healthRecordsByPokemonCenter")
    public String getHealthRecordsByPokemonCenter(HttpServletRequest request, Model model) {
        int pokemonCenterID = Integer.parseInt(request.getParameter("pokemonCenterID"));

        List<HealthRecord> healthRecords = service.getAllHealthRecordsByPokemonCenter(pokemonCenterID);
        List<PokemonCenter> pokemonCenters = service.getAllCenters(); // Fetch Pokemon Centers
        System.out.println("pokemonCenters");
        model.addAttribute("healthRecords", healthRecords);
        model.addAttribute("pokemonCenters", pokemonCenters); // Add Pokemon Centers to the model
        return "healthRecords"; // or any other view to display the health records
    }


    // Add a new health record - Process the form submission

    @PostMapping("/addHealthRecord")
    public String addHealthRecord(@ModelAttribute("healthRecord") @Valid HealthRecord healthRecord,
                                  BindingResult result,
                                  @RequestParam("pokemonID") int pokemonId,
                                  @RequestParam("pokemonCenterID") int pokemonCenterId,
                                  @RequestParam("date") String dateTimeString,
                                  @RequestParam("description") String description,
                                  Model model) {

        // Validate the input date and time format
        if("".equals(description)) {
            FieldError error = new FieldError("healthRecord", "description", "Health Record needs description");

            result.addError(error);
        }

        else {
            healthRecord.setDescription(description);
        }
          try {
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.US);
              LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
              healthRecord.setDate(dateTime);
          } catch (DateTimeParseException e) {
              e.printStackTrace();
              // Handle the exception, show an error message, or take any other actions as needed
              return "addHealthRecord";
          }


if(result.hasErrors() && result.getErrorCount()>1) {
    model.addAttribute("pokemons", service.getAllPokemon());
    model.addAttribute("pokemonCenter", service.getAllCenters());
    return "addHealthRecord";
}
        // Fetch the Pokemon and PokemonCenter objects from the ServiceInterface
        Pokemon pokemon = service.getPokemonById(pokemonId);
        PokemonCenter pokemonCenter = service.getCenterById(pokemonCenterId);

        // Set the Pokemon and PokemonCenter objects in the healthRecord
        healthRecord.setPokemon(pokemon);
        healthRecord.setPokemonCenter(pokemonCenter);

        // Rest of your code...

        service.addHealthRecord(healthRecord);
        return "redirect:/healthRecords";
    }


    // Show details of a specific health record
    //should have the pokemon type and the trainer
    @GetMapping("/detailHealthRecord")
    public String showHealthRecordDetail(@RequestParam("id") int id, Model model) {
        HealthRecord healthRecord = service.getHealthRecordById(id);
        model.addAttribute("healthRecord", healthRecord);
        return "detailHealthRecord";
    }



    // Edit a health record - Show the form
    @GetMapping("/editHealthRecord")
    public String showEditHealthRecordForm(@RequestParam("id") int id, Model model) {
        HealthRecord healthRecord = service.getHealthRecordById(id);
        List<Pokemon> pokemons = service.getAllPokemon();
        List<PokemonCenter> pokemonCenters = service.getAllCenters();
        model.addAttribute("healthRecord", healthRecord);
        model.addAttribute("pokemons", pokemons);
        model.addAttribute("pokemonCenters", pokemonCenters);
        return "editHealthRecord";
    }



    @PostMapping("/editHealthRecord")
    public String editHealthRecord(@ModelAttribute("healthRecord") @Valid HealthRecord healthRecord,
                                   BindingResult result,
                                   @RequestParam("pokemonID") int pokemonId,
                                   @RequestParam("pokemonCenterID") int pokemonCenterId,
                                   @RequestParam("id") int healthRecordId,
                                   @RequestParam("date") String dateTimeString,
                                   Model model) {

        // Rest of your code...

        try {
            // Convert the input date string to LocalDateTime using the date-time formatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a", Locale.US);
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
            healthRecord.setDate(dateTime);
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            // Handle the exception, show an error message, or take any other actions as needed
            return "editHealthRecord";
        }

        // Fetch the Pokemon and PokemonCenter objects from the ServiceInterface
        Pokemon pokemon = service.getPokemonById(pokemonId);
        PokemonCenter pokemonCenter = service.getCenterById(pokemonCenterId);

        // Set the Pokemon and PokemonCenter objects in the healthRecord
        healthRecord.setPokemon(pokemon);
        healthRecord.setPokemonCenter(pokemonCenter);

        // Rest of your code...

        service.updateHealthRecord(healthRecord);
        return "redirect:/healthRecords";
    }








    // Delete a health record
    @PostMapping("/deleteHealthRecord")
    public String deleteHealthRecord(@RequestParam("id") int id) {
        service.deleteHealthRecordById(id);
        return "redirect:/healthRecords";
    }
}
