package com.example.demo.controller;


import com.example.demo.model.Cita;
import com.example.demo.service.CitaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/citas")
public class CitaController {
    
    private final CitaService citaService;

    // Spring inyecta el service en el constructor
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }
    
    // LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("citas", citaService.listar());
        return "citas"; // pacientes.html
    }

    // MOSTRAR FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String nuevoPaciente(Model model) {
        model.addAttribute("cita", new Cita());
        return "formCita"; // formPaciente.html
    }

    // GUARDAR O ACTUALIZAR
    @PostMapping
    public String guardar(@ModelAttribute Cita cita) {
        citaService.guardar(cita);
        return "redirect:/citas";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cita cita = citaService.buscarPorId(id);
        model.addAttribute("cita", cita);
        return "formCita";
    }

    // ELIMINAR hola
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return "redirect:/citas";
    }
}
