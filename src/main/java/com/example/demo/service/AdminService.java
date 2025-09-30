package com.example.demo.service;

import com.example.demo.model.Admin;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminService {

    private final Admin adminPorDefecto;

    public AdminService() {
        // Crear admin por defecto
        this.adminPorDefecto = new Admin();
        adminPorDefecto.setName("Administrador");
        adminPorDefecto.setEmail("admin@clinica.com");
        adminPorDefecto.setPassword("123");
        System.out.println("✅ Admin creado: admin@clinica.com / 123");
    }

    public Optional<Admin> autenticar(String email, String password) {
        if (adminPorDefecto.getEmail().equals(email) &&
                adminPorDefecto.getPassword().equals(password)) {
            return Optional.of(adminPorDefecto);
        }
        return Optional.empty();
    }

    public Admin getAdminPorDefecto() {
        return adminPorDefecto;
    }
}