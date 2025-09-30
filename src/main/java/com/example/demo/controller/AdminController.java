package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.model.Paciente;
import com.example.demo.service.AdminService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/citasmedicas/admin")
public class AdminController {

    private final AdminService adminService;
    private final DoctorService doctorService;
    private final PacienteService pacienteService;

    public AdminController(AdminService adminService, DoctorService doctorService, PacienteService pacienteService) {
        this.adminService = adminService;
        this.doctorService = doctorService;
        this.pacienteService = pacienteService;
    }

    // 🔑 LOGIN ADMIN - Formulario
    @GetMapping("/login")
    public String loginAdmin() {
        return "admin/login";
    }

    // 🔑 LOGIN ADMIN - Procesar
    @PostMapping("/login")
    public String procesarLoginAdmin(@RequestParam String email,
                                     @RequestParam String password,
                                     Model model) {
        var adminOpt = adminService.autenticar(email, password);
        if (adminOpt.isPresent()) {
            return "redirect:/citasmedicas/admin/dashboard";
        }
        model.addAttribute("error", "Credenciales de administrador incorrectas");
        return "admin/login";
    }


    // 🏠 DASHBOARD ADMIN
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("admin", adminService.getAdminPorDefecto());
        model.addAttribute("doctores", doctorService.obtenerTodos()); // ← Agregar esta línea
        model.addAttribute("pacientes", pacienteService.obtenerTodos()); // ← Y esta línea
        model.addAttribute("totalDoctores", doctorService.obtenerTodos().size());
        model.addAttribute("totalPacientes", pacienteService.totalPacientes());
        return "admin/dashboard";
    }

    // 📊 PANEL PRINCIPAL
    @GetMapping
    public String panelAdmin() {
        return "redirect:/citasmedicas/admin/dashboard";
    }

    // 👨‍⚕️ GESTIÓN DE DOCTORES



    @GetMapping("/doctores/registrar")
    public String mostrarFormularioDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "admin/registrar-doctor";
    }

    @PostMapping("/doctores/registrar")
    public String registrarDoctor(@ModelAttribute Doctor doctor, Model model) {
        try {
            doctorService.registrarDoctor(doctor);
            return "redirect:/citasmedicas/admin/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar doctor: " + e.getMessage());
            return "admin/registrar-doctor";
        }
    }

    @GetMapping("/doctores/editar/{id}")
    public String mostrarEditarDoctor(@PathVariable Long id, Model model) {
        Doctor doctor = doctorService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));
        model.addAttribute("doctor", doctor);
        return "admin/editar-doctor";
    }

    @PostMapping("/doctores/editar/{id}")
    public String actualizarDoctor(@PathVariable Long id, @ModelAttribute Doctor doctor, Model model) {
        try {
            doctorService.actualizarDoctor(id, doctor);
            return "redirect:/citasmedicas/admin/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar doctor: " + e.getMessage());
            return "admin/editar-doctor";
        }
    }

    @PostMapping("/doctores/eliminar/{id}")
    public String eliminarDoctor(@PathVariable Long id) {
        doctorService.eliminarDoctor(id);
        return "redirect:/citasmedicas/admin/dashboard";
    }

    // 👥 GESTIÓN DE PACIENTES



    @GetMapping("/pacientes/editar/{id}")
    public String mostrarEditarPaciente(@PathVariable Long id, Model model) {
        Paciente paciente = pacienteService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        model.addAttribute("paciente", paciente);
        return "admin/editar-paciente";
    }

    @PostMapping("/pacientes/editar/{id}")
    public String actualizarPaciente(@PathVariable Long id, @ModelAttribute Paciente paciente, Model model) {
        try {
            pacienteService.actualizarPaciente(id, paciente);
            return "redirect:/citasmedicas/admin/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar paciente: " + e.getMessage());
            return "admin/editar-paciente";
        }
    }

    @PostMapping("/pacientes/eliminar/{id}")
    public String eliminarPaciente(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return "redirect:/citasmedicas/admin/dashboard";
    }
}