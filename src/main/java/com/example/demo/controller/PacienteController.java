package com.example.demo.controller;

import com.example.demo.model.Cita;
import com.example.demo.model.Disponibilidad;
import com.example.demo.model.Doctor;
import com.example.demo.model.Paciente;
import com.example.demo.model.Especialidad;
import com.example.demo.service.CitaService;
import com.example.demo.service.DoctorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/citasmedicas/paciente")
public class PacienteController {

    private final CitaService citaService;
    private final DoctorService doctorService;

    public PacienteController(CitaService citaService, DoctorService doctorService) {
        this.citaService = citaService;
        this.doctorService = doctorService;
    }

    // 🔹 Dashboard principal del paciente
    @GetMapping("/dashboard")
    public String dashboardPaciente(Model model, HttpSession session) {
        Paciente paciente = (Paciente) session.getAttribute("usuario");
        if (paciente == null) return "redirect:/citasmedicas/login?error=Debe iniciar sesión";

        model.addAttribute("paciente", paciente);
        model.addAttribute("especialidades", Especialidad.values()); // enum con las 6 especialidades
        model.addAttribute("citas", citaService.obtenerCitasPorPaciente(paciente));

        return "paciente/dashboard";
    }

    // 🔹 Filtrar doctores por especialidad (AJAX o fragmento Thymeleaf)
    @GetMapping("/doctores")
    @ResponseBody
    public List<Doctor> obtenerDoctoresPorEspecialidad(@RequestParam Especialidad especialidad) {
        return doctorService.obtenerPorEspecialidad(especialidad);
    }

    // 🔹 Obtener disponibilidades de un doctor específico
    @GetMapping("/disponibilidades")
    @ResponseBody
    public List<Disponibilidad> obtenerDisponibilidades(@RequestParam Long doctorId) {
        return citaService.obtenerDisponibilidadesPorDoctor(doctorId);
    }

    // 🔹 Agendar cita
    @PostMapping("/citas/agendar")
    public String agendarCita(@RequestParam Long doctorId,
                              @RequestParam Long disponibilidadId,
                              @RequestParam(required = false) String nota,
                              HttpSession session) {
        Paciente paciente = (Paciente) session.getAttribute("usuario");
        if (paciente == null) return "redirect:/citasmedicas/login";

        try {
            citaService.agendarCita(paciente, doctorId, disponibilidadId, nota);
            return "redirect:/citasmedicas/paciente/dashboard?success=Cita agendada&seccion=misCitas";
        } catch (IllegalArgumentException e) {
            return "redirect:/citasmedicas/paciente/dashboard?error=" + e.getMessage() + "&seccion=nuevaCita";
        }
    }

    // 🔹 Cancelar cita
    @PostMapping("/citas/cancelar/{citaId}")
    public String cancelarCita(@PathVariable Long citaId, HttpSession session) {
        Paciente paciente = (Paciente) session.getAttribute("usuario");
        if (paciente == null) return "redirect:/citasmedicas/login";

        try {
            citaService.cancelarCita(citaId, paciente);
            return "redirect:/citasmedicas/paciente/dashboard?success=Cita cancelada&seccion=misCitas";
        } catch (IllegalArgumentException e) {
            return "redirect:/citasmedicas/paciente/dashboard?error=" + e.getMessage() + "&seccion=misCitas";
        }
    }
}
