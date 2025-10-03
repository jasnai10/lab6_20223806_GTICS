package com.example.lab6.controller;

import com.example.lab6.entity.HeroeNaval;
import com.example.lab6.repository.HeroeNavalRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/heroes")
public class HeroeNavalController {

    final HeroeNavalRepository heroeNavalRepository;

    public HeroeNavalController(HeroeNavalRepository heroeNavalRepository){
        this.heroeNavalRepository = heroeNavalRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("heroes", heroeNavalRepository.findAll());
        return "heroes_lista";
    }

    @GetMapping("/nuevo")
    @PreAuthorize("hasRole('ADMIN')")
    public String nuevo(Model model) {
        model.addAttribute("heroe", new HeroeNaval());
        return "heroes_form";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public String guardar(@ModelAttribute HeroeNaval heroe) {
        heroeNavalRepository.save(heroe);
        return "redirect:/heroes";
    }

}

