package com.example.demo.controller;

import com.example.demo.model.Paciente;
import com.example.demo.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/citasmedicas/register")
public class RegisterController {

    private final PacienteService pacienteService;

    public RegisterController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "register";
    }

    @PostMapping
    public String registrarPaciente(@ModelAttribute Paciente paciente, Model model) {
        try {
            // FLUJO NORMAL - registro exitoso
            Paciente pacienteRegistrado = pacienteService.registrarPaciente(paciente);

            System.out.println("✅ Paciente registrado - ID: " + pacienteRegistrado.getId());
            System.out.println("📝 Nombre: " + pacienteRegistrado.getName());
            System.out.println("📧 Email: " + pacienteRegistrado.getEmail());
            System.out.println("📧 Email: " + pacienteRegistrado.getRol());

            return "redirect:/citasmedicas/login";

        } catch (IllegalArgumentException e) {
            // FLUJO EXCEPCIONAL - email duplicado
            System.out.println("❌ Error al registrar: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "register"; // Vuelve al formulario con mensaje de error
        }
    }

    // Endpoint opcional para ver todos los pacientes
    @GetMapping("/listar")
    @ResponseBody
    public String listarPacientes() {
        return "Total pacientes: " + pacienteService.totalPacientes();
    }
}
