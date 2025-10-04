package com.example.lab6.controller;

import com.example.lab6.entity.Mesa;
import com.example.lab6.entity.Reserva;
import com.example.lab6.entity.Usuario;
import com.example.lab6.repository.MesaRepository;
import com.example.lab6.repository.ReservaRepository;
import com.example.lab6.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    private final MesaRepository mesaRepo;
    private final ReservaRepository reservaRepo;
    private final UsuarioRepository usuarioRepo;

    public ReservaController(MesaRepository mesaRepo, ReservaRepository reservaRepo, UsuarioRepository usuarioRepo) {
        this.mesaRepo = mesaRepo;
        this.reservaRepo = reservaRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping
    public String verMesas(Model model, Principal principal) {
        Usuario usuario = usuarioRepo.findByCorreo(principal.getName()).orElse(null);

        model.addAttribute("mesasDisponibles", mesaRepo.findByDisponibleTrue());
        model.addAttribute("ocupadas", mesaRepo.countByDisponibleFalse());
        model.addAttribute("libres", mesaRepo.countByDisponibleTrue());
        model.addAttribute("miReserva", reservaRepo.findByUsuario(usuario).orElse(null));

        return "reserva/lista";
    }

    // Reservar mesa
    @PostMapping("/reservar/{mesaId}")
    public String reservarMesa(@PathVariable Integer mesaId,
                               Principal principal,
                               Model model) {
        Usuario usuario = usuarioRepo.findByCorreo(principal.getName()).orElse(null);


        if (reservaRepo.findByUsuario(usuario).isPresent()) {
            model.addAttribute("error", "Ya tienes una mesa reservada.");
            return "redirect:/reserva";
        }

        Mesa mesa = mesaRepo.findById(mesaId).orElse(null);
        if (mesa == null || !mesa.getDisponible()) {
            model.addAttribute("error", "La mesa ya no está disponible.");
            return "redirect:/reserva";
        }


        if (mesa.getCapacidad() > 4) {
            model.addAttribute("error", "Una mesa no puede tener más de 4 asientos.");
            return "redirect:/reserva";
        }

        // crear reserva
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setMesa(mesa);
        reserva.setFecha(Timestamp.from(Instant.now()));
        reservaRepo.save(reserva);

        mesa.setDisponible(false);
        mesaRepo.save(mesa);

        return "redirect:/reserva";
    }
}
