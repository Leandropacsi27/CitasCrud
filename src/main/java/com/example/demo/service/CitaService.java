package com.example.demo.service;

import com.example.demo.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaService {

    private final List<Cita> citas = new ArrayList<>();
    private long nextCitaId = 1L;

    private final DoctorService doctorService;

    public CitaService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // ✅ Agendar cita
    public Cita agendarCita(Paciente paciente, Long doctorId, Long disponibilidadId, String nota) {
        Doctor doctor = doctorService.buscarPorId(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));

        Disponibilidad disponibilidad = obtenerDisponibilidadValida(doctor, disponibilidadId);

        if (existeCitaEnDisponibilidad(disponibilidadId)) {
            throw new IllegalArgumentException("Esta disponibilidad ya está ocupada");
        }

        Cita cita = new Cita();
        cita.setId(nextCitaId++);
        cita.setPaciente(paciente);
        cita.setDoctor(doctor);
        cita.setDisponibilidad(disponibilidad);
        cita.setNota(nota);

        citas.add(cita);
        return cita;
    }

    // ✅ Citas por paciente
    public List<Cita> obtenerCitasPorPaciente(Paciente paciente) {
        return citas.stream()
                .filter(cita -> cita.getPaciente().getId().equals(paciente.getId()))
                .collect(Collectors.toList());
    }

    // ✅ Disponibilidades de un doctor
    public List<Disponibilidad> obtenerDisponibilidadesPorDoctor(Long doctorId) {
        return doctorService.buscarPorId(doctorId)
                .map(Doctor::getDisponibilidad)
                .orElse(Collections.emptyList());
    }



    // ✅ Cancelar cita
    public void cancelarCita(Long citaId, Paciente paciente) {
        Cita cita = citas.stream()
                .filter(c -> c.getId().equals(citaId) && c.getPaciente().getId().equals(paciente.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        citas.remove(cita);
    }

    // 🔒 Helpers privados
    private Disponibilidad obtenerDisponibilidadValida(Doctor doctor, Long disponibilidadId) {
        if (doctor.getDisponibilidad() == null) {
            throw new IllegalArgumentException("El doctor no tiene disponibilidades");
        }

        return doctor.getDisponibilidad().stream()
                .filter(d -> d.getId().equals(disponibilidadId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Disponibilidad no encontrada"));
    }

    private boolean existeCitaEnDisponibilidad(Long disponibilidadId) {
        return citas.stream()
                .anyMatch(cita -> cita.getDisponibilidad().getId().equals(disponibilidadId));
    }
}
