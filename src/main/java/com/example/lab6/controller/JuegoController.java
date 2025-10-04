package com.example.lab6.controller;

import com.example.lab6.entity.AsignacionCancion;
import com.example.lab6.entity.Usuario;
import com.example.lab6.repository.AsignacionCancionRepository;
import com.example.lab6.repository.UsuarioRepository;
import com.example.lab6.service.JuegoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/juego")
public class JuegoController {

    private final AsignacionCancionRepository asignacionRepo;
    private final UsuarioRepository usuarioRepo;
    private final JuegoService juegoService;

    public JuegoController(AsignacionCancionRepository asignacionRepo,
                           UsuarioRepository usuarioRepo,
                           JuegoService juegoService) {
        this.asignacionRepo = asignacionRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoService = juegoService;
    }

    @GetMapping
    public String mostrarJuego(Principal principal, Model model) {
        Usuario usuario = usuarioRepo.findByCorreo(principal.getName()).orElse(null);
        if (usuario == null) return "redirect:/login";

        AsignacionCancion asignacion = asignacionRepo.findByUsuarioAndAdivinadaFalse(usuario).orElse(null);

        if (asignacion == null) {
            model.addAttribute("mensaje", "No tienes ninguna canción asignada todavía.");
            return "juego/noSong";
        }

        model.addAttribute("asignacion", asignacion);
        return "juego/jugar";
    }

    @PostMapping("/intentar")
    public String procesarIntento(@RequestParam String intento,
                                  Principal principal,
                                  Model model) {
        Usuario usuario = usuarioRepo.findByCorreo(principal.getName()).orElse(null);
        AsignacionCancion asignacion = asignacionRepo.findByUsuarioAndAdivinadaFalse(usuario).orElse(null);

        if (asignacion == null) {
            model.addAttribute("mensaje", "No tienes canción asignada.");
            return "juego/noSong";
        }

        String resultado = juegoService.compararIntento(asignacion, intento);
        asignacionRepo.save(asignacion);

        model.addAttribute("resultado", resultado);
        model.addAttribute("asignacion", asignacion);

        return "juego/jugar";
    }

    @GetMapping("/ranking")
    public String verRanking(Model model) {
        List<AsignacionCancion> top10 = asignacionRepo.findTop10ByAdivinadaTrueOrderByIntentosAsc();
        model.addAttribute("ranking", top10);
        return "juego/ranking";
    }
}
