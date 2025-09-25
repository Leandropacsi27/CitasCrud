package com.example.demo.service;

import com.example.demo.model.Doctor;
import com.example.demo.model.Paciente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    private final List<Doctor> doctores = new ArrayList<>();
    private Long idCounter = 1L;

    // LISTAR
    public List<Doctor> listar() {
        return doctores;
    }

    public DoctorService() {
        // Pacientes de ejemplo al arrancar la app
        doctores.add(new Doctor(1L, "Luis M", "luis@example.com", "pass1"));
        doctores.add(new Doctor(2L, "Ana T", "ana@example.com", "pass2"));
    }

    // GUARDAR O ACTUALIZAR
    public void guardar(Doctor doctor) {
        if (doctor.getId() == null) {
            doctor.setId(idCounter++);
            doctores.add(doctor);
        } else { // actualizar
            for (int i = 0; i < doctores.size(); i++) {
                if (doctores.get(i).getId().equals(doctor.getId())) {
                    doctores.set(i, doctor);
                    break;
                }
            }
        }
    }

    // ELIMINAR
    public void eliminar(Long id) {
        doctores.removeIf(p -> p.getId().equals(id));
    }

    // BUSCAR POR ID (para editar)
    public Doctor buscarPorId(Long id) {
        return doctores.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
