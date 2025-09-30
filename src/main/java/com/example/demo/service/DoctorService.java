package com.example.demo.service;

import com.example.demo.model.Doctor;
import com.example.demo.model.Especialidad;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DoctorService {

    private final List<Doctor> doctores = new ArrayList<>();

    public DoctorService() {
        // Doctor de ejemplo
        doctores.add(new Doctor("Dr. Juan Pérez", "juanperez@example.com", "123", Especialidad.CARDIOLOGIA));
    }

    public List<Doctor> obtenerTodos() {
        return new ArrayList<>(doctores);
    }

    public Doctor registrarDoctor(Doctor doctor) {
        if (doctor.getRol() == null) {
            doctor.setRol(Doctor.Rol.DOCTOR);
        }

        if (doctor.getId() == null) {
            doctor.asignarId();
        }

        doctores.add(doctor);
        return doctor;
    }

    public Optional<Doctor> buscarPorId(Long id) {
        return doctores.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    public void actualizarDoctor(Long id, Doctor doctorActualizado) {
        Doctor doctorExistente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));

        doctorExistente.setName(doctorActualizado.getName());
        doctorExistente.setEmail(doctorActualizado.getEmail());
        doctorExistente.setPassword(doctorActualizado.getPassword());
        doctorExistente.setEspecialidad(doctorActualizado.getEspecialidad());
        // Actualizar especialidad si existe el campo
    }

    public void eliminarDoctor(Long id) {
        doctores.removeIf(d -> d.getId().equals(id));
    }

    public Optional<Doctor> autenticar(String email, String password) {
        return doctores.stream()
                .filter(d -> d.getEmail().equals(email) && d.getPassword().equals(password))
                .findFirst();
    }


    public List<Doctor> obtenerPorEspecialidad(Especialidad especialidad) {
        return doctores.stream()
                .filter(d -> d.getEspecialidad() == especialidad)
                .collect(Collectors.toList());
    }

}