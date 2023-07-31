package com.fk.pokemoncare.controller;

import com.fk.pokemoncare.entities.HealthRecord;
import com.fk.pokemoncare.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ServiceInterface service;

    @GetMapping("/")
    public String index(Model model) {

        List<HealthRecord>  healthRecords = service.getAllHealthRecords();
        model.addAttribute("healthRecords", healthRecords);
        if (healthRecords.size() > 10) {
            healthRecords= healthRecords.subList(0, 10);
        }
        model.addAttribute("healthRecords", healthRecords);
        return "index";
    }


}
