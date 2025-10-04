package com.example.lab6.service;

import com.example.lab6.entity.AsignacionCancion;
import org.springframework.stereotype.Service;

@Service
public class JuegoService {

    public String compararIntento(AsignacionCancion asignacion, String intento) {
        String titulo = asignacion.getCancion().getTitulo();


        intento = intento.trim().toLowerCase();
        titulo = titulo.trim().toLowerCase();

        StringBuilder resultado = new StringBuilder();
        int correctas = 0;

        for (int i = 0; i < titulo.length(); i++) {
            char letraReal = titulo.charAt(i);
            char letraIntento = i < intento.length() ? intento.charAt(i) : '-';

            if (letraReal == letraIntento) {
                resultado.append(letraReal);
                correctas++;
            } else {
                resultado.append("_");
            }
        }

        asignacion.setIntentos(asignacion.getIntentos() + 1);

        // Aquí colocaré mi lógica para cuando adivine todo
        if (correctas == titulo.length() && intento.length() == titulo.length()) {
            asignacion.setAdivinada(true);
            return "¡FELICIDADES! Adivinaste en " + asignacion.getIntentos() + " intentos.";
        }

        return "Coincidencias: " + correctas + "/" + titulo.length() + " → " + resultado;
    }
}
