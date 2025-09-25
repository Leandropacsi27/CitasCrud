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
<<<<<<< HEAD

        idCounter = doctores.stream()
                .mapToLong(Doctor::getId)
                .max()
                .orElse(0L) + 1L;
=======
>>>>>>> a18938dc923f655078229d11ae2c320ea376ae59
    }

    // GUARDAR O ACTUALIZAR
    public void guardar(Doctor doctor) {
        if (doctor.getId() == null) {
<<<<<<< HEAD
            // NUEVO paciente - usa idCounter sincronizado
            doctor.setId(idCounter++);
            doctores.add(doctor);
        } else {
            // ACTUALIZAR paciente existente
            for (int i = 0; i < doctores.size(); i++) {
                if (doctores.get(i).getId().equals(doctor.getId())) {

                    // ✅ Mantener password si viene vacío
                    if (doctor.getPassword() == null ||
                            doctor.getPassword().trim().isEmpty()) {
                        doctor.setPassword(doctores.get(i).getPassword());
                    }

=======
            doctor.setId(idCounter++);
            doctores.add(doctor);
        } else { // actualizar
            for (int i = 0; i < doctores.size(); i++) {
                if (doctores.get(i).getId().equals(doctor.getId())) {
>>>>>>> a18938dc923f655078229d11ae2c320ea376ae59
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
