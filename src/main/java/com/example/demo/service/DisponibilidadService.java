package com.example.demo.service;

import com.example.demo.model.Doctor;
import com.example.demo.model.Disponibilidad;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DisponibilidadService {

    private final DoctorService doctorService;
    private long nextDisponibilidadId = 1L;

    public DisponibilidadService(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public Disponibilidad agregarDisponibilidad(Long doctorId, Disponibilidad disp) {
        Doctor doctor = doctorService.buscarPorId(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor no encontrado"));

        disp.setId(nextDisponibilidadId++);
        disp.setDoctor(doctor);

        doctor.getDisponibilidad().add(disp);

        return disp;
    }

    public void eliminarDisponibilidad(Doctor doctor, Long dispId) {
        if (doctor.getDisponibilidad() != null) {
            doctor.getDisponibilidad().removeIf(d -> d.getId().equals(dispId));
        }
    }

    public void actualizarDisponibilidad(Doctor doctor, Disponibilidad dispActualizada) {
        if (doctor.getDisponibilidad() != null) {
            Optional<Disponibilidad> opt = doctor.getDisponibilidad().stream()
                    .filter(d -> d.getId().equals(dispActualizada.getId()))
                    .findFirst();
            if (opt.isPresent()) {
                Disponibilidad dispExistente = opt.get();
                dispExistente.setFecha(dispActualizada.getFecha());
                dispExistente.setHoraInicio(dispActualizada.getHoraInicio());
                dispExistente.setHoraFin(dispActualizada.getHoraFin());
            } else {
                throw new IllegalArgumentException("Disponibilidad no encontrada");
            }
        }
    }

    public List<Disponibilidad> obtenerDisponibilidades(Doctor doctor) {
        if (doctor.getDisponibilidad() == null) doctor.setDisponibilidad(new ArrayList<>());
        return doctor.getDisponibilidad();
    }
}
