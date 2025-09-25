package com.example.demo.service;

import com.example.demo.model.Paciente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PacienteService {

    private final List<Paciente> pacientes = new ArrayList<>();
    private Long idCounter = 1L;

    // LISTAR
    public List<Paciente> listar() {
        return pacientes;
    }

    public PacienteService() {
        // Pacientes de ejemplo al arrancar la app
        pacientes.add(new Paciente(1L, "Luis Torres", "luis@example.com", "pass1"));
        pacientes.add(new Paciente(2L, "Ana Ramos", "ana@example.com", "pass2"));

        idCounter = pacientes.stream()
                .mapToLong(Paciente::getId)
                .max()
                .orElse(0L) + 1L;
    }


    // GUARDAR O ACTUALIZAR (MEJORADO)
    public void guardar(Paciente paciente) {
        if (paciente.getId() == null) {
            // NUEVO paciente - usa idCounter sincronizado
            paciente.setId(idCounter++);
            pacientes.add(paciente);
        } else {
            // ACTUALIZAR paciente existente
            for (int i = 0; i < pacientes.size(); i++) {
                if (pacientes.get(i).getId().equals(paciente.getId())) {

                    // ✅ Mantener password si viene vacío
                    if (paciente.getPassword() == null ||
                            paciente.getPassword().trim().isEmpty()) {
                        paciente.setPassword(pacientes.get(i).getPassword());
                    }

                    pacientes.set(i, paciente);
                    break;
                }
            }
        }
    }

    // ELIMINAR
    public void eliminar(Long id) {
        pacientes.removeIf(p -> p.getId().equals(id));
    }

    // BUSCAR POR ID (para editar)
    public Paciente buscarPorId(Long id) {
        return pacientes.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
