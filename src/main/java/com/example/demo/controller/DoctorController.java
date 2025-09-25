package com.example.demo.controller;


import com.example.demo.model.Doctor;
import com.example.demo.model.Paciente;
import com.example.demo.service.DoctorService;
<<<<<<< HEAD
=======
import com.example.demo.service.PacienteService;
>>>>>>> a18938dc923f655078229d11ae2c320ea376ae59
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctores")
public class DoctorController {

    private final DoctorService doctorService;

    // Spring inyecta el service en el constructor
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("doctores", doctorService.listar());
        return "doctores"; // pacientes.html
    }

    // MOSTRAR FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String nuevoPaciente(Model model) {
        model.addAttribute("doctor", new Paciente());
        return "formDoctor"; // formPaciente.html
    }

    // GUARDAR O ACTUALIZAR
    @PostMapping
    public String guardar(@ModelAttribute Doctor doctor) {
        doctorService.guardar(doctor);
        return "redirect:/doctores";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
<<<<<<< HEAD
        Doctor doctor = doctorService.buscarPorId(id);
        model.addAttribute("doctor", doctor);
=======
        model.addAttribute("doctor", doctorService.buscarPorId(id));
>>>>>>> a18938dc923f655078229d11ae2c320ea376ae59
        return "formDoctor";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        doctorService.eliminar(id);
        return "redirect:/doctores";
    }
}
