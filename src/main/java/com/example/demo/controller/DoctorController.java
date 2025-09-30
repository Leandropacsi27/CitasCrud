package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.model.Disponibilidad;
import com.example.demo.service.DisponibilidadService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/citasmedicas/doctor")
public class DoctorController {

    private final DisponibilidadService disponibilidadService;

    public DoctorController(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

    @GetMapping("/dashboard")
    public String dashboardDoctor(Model model, HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("usuario");
        if (doctor == null) return "redirect:/citasmedicas/login?error=Debe iniciar sesión";

        model.addAttribute("doctor", doctor);
        model.addAttribute("disponibilidades", disponibilidadService.obtenerDisponibilidades(doctor));
        return "doctor/dashboard";
    }

    @PostMapping("/disponibilidad/guardar")
    public String guardarDisponibilidad(@ModelAttribute Disponibilidad disp, HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("usuario");
        if (doctor == null) return "redirect:/citasmedicas/login";

        // ✅ Guardar en el service por ID (se actualiza la lista global de doctores)
        disponibilidadService.agregarDisponibilidad(doctor.getId(), disp);

        return "redirect:/citasmedicas/doctor/dashboard";
    }


    @GetMapping("/disponibilidad/eliminar/{id}")
    public String eliminarDisponibilidad(@PathVariable Long id, HttpSession session) {
        Doctor doctor = (Doctor) session.getAttribute("usuario");
        if (doctor == null) return "redirect:/citasmedicas/login";

        disponibilidadService.eliminarDisponibilidad(doctor, id);
        return "redirect:/citasmedicas/doctor/dashboard";
    }
}

