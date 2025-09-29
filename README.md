🩺 Sistema de Gestión de Citas Médicas

Este proyecto es una aplicación básica en Spring Boot que permite gestionar pacientes, doctores y citas médicas.
Actualmente los datos se manejan en memoria (sin base de datos), lo cual lo hace ideal para pruebas y aprendizaje.

🚀 Características

Registro de doctores con especialidad (limitada a un conjunto definido con enum).

Registro de pacientes.

Creación de citas médicas filtrando doctores por especialidad y disponibilidad.

Manejo de sesión con HTTP Session (los pacientes son los únicos que pueden crear citas).

Implementado con Thymeleaf y Bootstrap 5 en el frontend.

📂 Estructura del proyecto
src/
 └── main/
      ├── java/com/example/demo/
      │    ├── controller/   # Controladores (CitaController, DoctorController, etc.)
      │    ├── model/        # Modelos (Doctor, Paciente, Cita, Disponibilidad, Especialidad, etc.)
      │    ├── service/      # Servicios en memoria
      │    └── DemoApplication.java
      └── resources/
           ├── templates/    # Vistas Thymeleaf
           └── application.properties

⚙️ Requisitos

Java 17+ (o tu versión instalada, ej. 23.0.1 si es la que usas).

Maven 3.9+

Un IDE como IntelliJ IDEA o NetBeans.

▶️ Ejecución

Clonar el repositorio:

git clone https://github.com/tuusuario/sistema-citas-medicas.git
cd sistema-citas-medicas


Compilar y ejecutar con Maven:

Abrir en el navegador:

http://localhost:8080/login -> Para ingresar pacientes y doctores
http://localhost:8080/register -> Para registrar solo pacientes


👨‍⚕️ Roles

Pacientes: pueden crear citas.

Doctores: se registran con nombre, email, contraseña y especialidad.


📝 Notas importantes

Actualmente, todos los datos se guardan en listas en memoria → cuando reinicies la app, se perderán.

Si más adelante quieres persistencia real, se puede integrar con Spring Data JPA + H2/MySQL fácilmente.

El formulario de doctores usa un select basado en Especialidad (enum), para evitar especialidades inventadas.

📌 Próximos pasos / TODO

 Agregar base de datos (H2 o MySQL).

 Manejo de validaciones en formularios.

 Autenticación con Spring Security.

 CRUD de disponibilidades para doctores.
