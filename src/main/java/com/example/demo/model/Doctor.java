package com.example.demo.model;

import java.util.List;

public class Doctor extends Usuario {
    private Especialidad especialidad;
    private List<Disponibilidad> disponibilidad;

    public Doctor() {
        this.setRol(Rol.DOCTOR);
    }

    public Doctor(String name, String email, String password, Especialidad especialidad) {
        super(name, email, password, Rol.DOCTOR);
        this.especialidad = especialidad;
    }

    // 🔥 GETTERS Y SETTERS FALTANTES
    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public List<Disponibilidad> getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(List<Disponibilidad> disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}