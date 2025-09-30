package com.example.demo.controller;


import com.example.demo.model.Doctor;
import com.example.demo.model.Paciente;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PacienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 *
 * @author Leandro
 */

@Controller
@RequestMapping("/citasmedicas/login")
public class LoginController {

    private final DoctorService doctorService;
    private final PacienteService pacienteService;

    // Inyectar servicios en el constructor
    public LoginController(DoctorService doctorService, PacienteService pacienteService) {
        this.doctorService = doctorService;
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public String login() {
        return "login";
    }

    @PostMapping
    public String procesarLogin(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {

        // 1. Intentar autenticar como DOCTOR primero
        Optional<Doctor> doctorOpt = doctorService.autenticar(email, password);
        if (doctorOpt.isPresent()) {
            session.setAttribute("usuario", doctorOpt.get());
            session.setAttribute("rol", "DOCTOR");
            return "redirect:/citasmedicas/doctor/dashboard";
        }

        // 2. Intentar autenticar como PACIENTE
        Optional<Paciente> pacienteOpt = pacienteService.autenticar(email, password);
        if (pacienteOpt.isPresent()) {
            session.setAttribute("usuario", pacienteOpt.get());
            session.setAttribute("rol", "PACIENTE");
            return "redirect:/citasmedicas/paciente/dashboard";
        }

        // 3. Si no se autenticó en ninguno
        model.addAttribute("error", "Email o contraseña incorrectos");
        return "login";
    }
}
