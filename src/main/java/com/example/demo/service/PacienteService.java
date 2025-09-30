package com.example.demo.service;

import com.example.demo.model.Paciente;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final List<Paciente> pacientes = new ArrayList<>();


    public PacienteService() {
        // Datos iniciales
        pacientes.add(new Paciente("Luis M", "luis@example.com", "123"));
    }

    public Paciente registrarPaciente(Paciente paciente) {
        // 1. Validar que el email no exista
        if (buscarPorEmail(paciente.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email " + paciente.getEmail() + " ya está registrado");
        }

        // 2. Asignar ID automático
        paciente.asignarId();

        // 3. Guardar en la lista
        pacientes.add(paciente);

        return paciente; // Devuelve el paciente directamente, sin Optional
    }

    // Buscar paciente por email
    public Optional<Paciente> buscarPorEmail(String email) {
        return pacientes.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    // Obtener todos los pacientes
    public List<Paciente> obtenerTodos() {
        return new ArrayList<>(pacientes);
    }

    // Buscar paciente por ID
    public Optional<Paciente> buscarPorId(Long id) {
        return pacientes.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    // Contar total de pacientes
    public int totalPacientes() {
        return pacientes.size();
    }

    public void actualizarPaciente(Long id, Paciente pacienteActualizado) {
        Paciente pacienteExistente = buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        pacienteExistente.setName(pacienteActualizado.getName());
        pacienteExistente.setEmail(pacienteActualizado.getEmail());
        pacienteExistente.setPassword(pacienteActualizado.getPassword());
    }

    public void eliminarPaciente(Long id) {
        pacientes.removeIf(p -> p.getId().equals(id));
    }

    public Optional<Paciente> autenticar(String email, String password) {
        return pacientes.stream()
                .filter(p -> p.getEmail().equals(email) && p.getPassword().equals(password))
                .findFirst();
    }
}
