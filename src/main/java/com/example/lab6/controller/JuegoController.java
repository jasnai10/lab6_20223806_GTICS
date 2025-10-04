package com.example.lab6.controller;

import com.example.lab6.entity.AsignacionCancion;
import com.example.lab6.entity.CancionCriolla;
import com.example.lab6.entity.Usuario;
import com.example.lab6.repository.AsignacionCancionRepository;
import com.example.lab6.repository.UsuarioRepository;
import com.example.lab6.repository.CancionCriollaRepository;
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

    public JuegoController(AsignacionCancionRepository asignacionRepo,
                           UsuarioRepository usuarioRepo) {
        this.asignacionRepo = asignacionRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping("")
    public String mostrarJuego(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return "redirect:/login";
        }

        // Buscar asignación activa
        AsignacionCancion asignacion = asignacionRepo.findByUsuario(usuario)
                .orElse(null);

        if (asignacion == null || asignacion.getCancion() == null) {
            return "juego/noSong";
        }

        CancionCriolla cancion = asignacion.getCancion();
        String titulo = cancion.getTitulo();

        // Construir patrón estilo ahorcado
        StringBuilder patron = new StringBuilder();
        int letras = 0;
        int espacios = 0;

        for (char c : titulo.toCharArray()) {
            if (c == ' ') {
                patron.append(" ");
                espacios++;
            } else {
                patron.append("_ ");
                letras++;
            }
        }

        model.addAttribute("asignacion", asignacion);
        model.addAttribute("titulo", titulo);
        model.addAttribute("patron", patron.toString().trim());
        model.addAttribute("letras", letras);
        model.addAttribute("espacios", espacios);
        model.addAttribute("longitud", titulo.length());

        return "juego/jugar";
    }


    @PostMapping("/adivinar")
    public String procesarIntento(@RequestParam String intento,
                                  Principal principal,
                                  Model model) {
        Usuario usuario = usuarioRepo.findByCorreo(principal.getName()).orElse(null);
        AsignacionCancion asignacion = asignacionRepo.findByUsuarioAndAdivinadaFalse(usuario).orElse(null);

        if (asignacion == null) {
            model.addAttribute("mensaje", "No tienes canción asignada.");
            return "juego/sinCancion";
        }

        String titulo = asignacion.getCancion().getTitulo().toLowerCase();
        intento = intento.toLowerCase();

        int correctas = 0;
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < titulo.length(); i++) {
            char real = titulo.charAt(i);
            char guess = i < intento.length() ? intento.charAt(i) : '-';
            if (real == guess) {
                resultado.append(real);
                correctas++;
            } else {
                resultado.append("_");
            }
        }

        asignacion.setIntentos(asignacion.getIntentos() + 1);

        if (correctas == titulo.length() && intento.length() == titulo.length()) {
            asignacion.setAdivinada(true);
            model.addAttribute("resultado", "🎉 ¡FELICIDADES! Adivinaste en " + asignacion.getIntentos() + " intentos.");
        } else {
            model.addAttribute("resultado", "Coincidencias: " + correctas + "/" + titulo.length() + " → " + resultado);
        }

        asignacionRepo.save(asignacion);
        model.addAttribute("asignacion", asignacion);

        return "juego/jugar";
    }

    @PostMapping("/solicitar")
    public String solicitarAsignacion(Principal principal) {
        Usuario usuario = usuarioRepo.findByCorreo(principal.getName()).orElse(null);
        if (usuario == null) return "redirect:/login";

        // Crear solicitud solo si no tiene ya
        if (asignacionRepo.findByUsuarioAndAdivinadaFalse(usuario).isEmpty()) {
            AsignacionCancion nueva = new AsignacionCancion();
            nueva.setUsuario(usuario);
            nueva.setIntentos(0);
            nueva.setAdivinada(false);
            nueva.setCancion(null); // sin canción aún
            asignacionRepo.save(nueva);
        }

        return "redirect:/juego";
    }

    @GetMapping("/ranking")
    public String verRanking(Model model) {
        List<AsignacionCancion> top10 = asignacionRepo.findTop10ByAdivinadaTrueOrderByIntentosAsc();
        model.addAttribute("ranking", top10);
        return "juego/ranking";
    }
}
