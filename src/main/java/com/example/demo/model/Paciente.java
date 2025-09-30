package com.example.demo.model;

import java.util.List;

public class Paciente extends Usuario {
    private List<Cita> cita;

    public Paciente() {
        this.setRol(Rol.PACIENTE);
    }

    public Paciente(String name, String email, String password) {
        super(name, email, password, Rol.PACIENTE);
    }

    public List<Cita> getCita() {
        return cita;
    }

    public void setCita(List<Cita> cita) {
        this.cita = cita;
    }
}
