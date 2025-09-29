package com.example.demo.model;

public class Admin extends Usuario {

    public Admin() {
        this.setRol(Rol.ADMIN);
    }

    public Admin(String name, String email, String password) {
        super(name, email, password, Rol.ADMIN);
    }
}
