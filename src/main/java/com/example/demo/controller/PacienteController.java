package com.example.demo.controller;

import com.example.demo.model.Paciente;
import com.example.demo.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    // Spring inyecta el service en el constructor
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", pacienteService.listar());
        return "pacientes"; // pacientes.html
    }

    // MOSTRAR FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String nuevoPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "formPaciente"; // formPaciente.html
    }

    // GUARDAR O ACTUALIZAR
    @PostMapping
    public String guardar(@ModelAttribute Paciente paciente) {
        pacienteService.guardar(paciente);
        return "redirect:/pacientes";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Paciente paciente = pacienteService.buscarPorId(id);
        model.addAttribute("paciente",paciente);
        return "formPaciente";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        pacienteService.eliminar(id);
        return "redirect:/pacientes";
    }
}
