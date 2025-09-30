package com.example.demo.model;

/**
 *
 * @author Leandro
 */
public class Usuario {
    private static Long contador = 1L;
    private Long id;
    private String name;
    private String email;
    private String password;
    private Rol rol;

    // ENUM dentro de la misma clase (más simple para ti)
    public enum Rol {
        PACIENTE,
        DOCTOR,
        ADMIN
    }

    // ========== CONSTRUCTORES ==========

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros (ID automático)
    public Usuario(String name, String email, String password, Rol rol) {
        this.id = contador++;
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public void asignarId() {
        this.id = contador++;
    }

    // Métodos para verificar roles
    public boolean esAdmin() {
        return this.rol == Rol.ADMIN;
    }

    public boolean esDoctor() {
        return this.rol == Rol.DOCTOR;
    }

    public boolean esPaciente() {
        return this.rol == Rol.PACIENTE;
    }

    // Metodo para obtener URL del dashboard según rol
    public String getDashboardUrl() {
        switch (this.rol) {
            case ADMIN: return "/admin/dashboard";
            case DOCTOR: return "/doctor/dashboard";
            case PACIENTE: return "/paciente/dashboard";
            default: return "/";
        }
    }

    // ========== GETTERS Y SETTERS ==========

    public static Long getContador() {
        return contador;
    }

    public static void setContador(Long contador) {
        Usuario.contador = contador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // ========== MÉTODOS ADICIONALES ==========

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", rol=" + rol +
                '}';
    }

    // Para comparar usuarios (útil para login)
    public boolean credencialesValidas(String email, String password) {
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }
}
