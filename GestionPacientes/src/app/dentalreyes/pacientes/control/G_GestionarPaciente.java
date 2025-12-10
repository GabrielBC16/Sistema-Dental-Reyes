package app.dentalreyes.pacientes.control;

import app.dentalreyes.entidades.Paciente;
import java.sql.Date;

/**
 * CONTROLADOR UNIFICADO PARA GESTIÓN DE PACIENTES
 * Maneja Registrar, Buscar, Actualizar y Eliminar en un solo lugar.
 */
public class G_GestionarPaciente {

    private final PacienteDAO pacienteDAO;

    public G_GestionarPaciente() {
        this.pacienteDAO = new PacienteDAO();
    }

    // ==========================================
    // 1. REGISTRAR PACIENTE
    // ==========================================
    public String registrarPaciente(String dni, String nombres, String apellidos, 
                                    java.util.Date fechaUtil, String sexo, String telefono, 
                                    String direccion, String correo) {
        
        // Validar datos antes de crear el objeto
        String error = validarDatos(dni, nombres, apellidos, fechaUtil, correo, telefono);
        if (error != null) return error;

        // Convertir y Crear
        Paciente nuevoPaciente = crearObjetoPaciente(dni, nombres, apellidos, fechaUtil, sexo, telefono, direccion, correo);

        // Llamar al DAO
        if (pacienteDAO.registrar(nuevoPaciente)) {
            return "Éxito: Paciente registrado correctamente.";
        } else {
            return "Error: No se pudo guardar. Verifique si el DNI ya existe.";
        }
    }

    // ==========================================
    // 2. ACTUALIZAR PACIENTE
    // ==========================================
    public String actualizarPaciente(String dni, String nombres, String apellidos,
                                     java.util.Date fechaUtil, String sexo, String telefono,
                                     String direccion, String correo) {

        String error = validarDatos(dni, nombres, apellidos, fechaUtil, correo, telefono);
        if (error != null) return error;

        Paciente pacienteEditado = crearObjetoPaciente(dni, nombres, apellidos, fechaUtil, sexo, telefono, direccion, correo);

        if (pacienteDAO.actualizar(pacienteEditado)) {
            return "Éxito: Paciente actualizado correctamente.";
        } else {
            return "Error: No se pudo actualizar el paciente.";
        }
    }

    // ==========================================
    // 3. ELIMINAR PACIENTE
    // ==========================================
    public String eliminarPaciente(String dni) {
        if (dni == null || dni.trim().length() != 8) {
            return "Error: DNI inválido.";
        }

        if (pacienteDAO.eliminar(dni)) {
            return "Éxito: Paciente eliminado correctamente.";
        } else {
            return "Error: No se pudo eliminar el paciente.";
        }
    }

    // ==========================================
    // 4. BUSCAR PACIENTE
    // ==========================================
    public Paciente buscarPaciente(String dni) {
        if (dni == null || dni.trim().length() != 8) {
            return null;
        }
        return pacienteDAO.buscarPorDni(dni);
    }

    // ==========================================
    // MÉTODOS AUXILIARES (PRIVADOS)
    // Para no repetir código de validación
    // ==========================================
    
    private String validarDatos(String dni, String nom, String ape, java.util.Date fecha, String correo, String tel) {
        if (dni == null || dni.trim().length() != 8 || !dni.matches("\\d+")) {
            return "Error: El DNI debe tener exactamente 8 dígitos numéricos.";
        }
        if (nom.isEmpty() || ape.isEmpty()) {
            return "Error: Nombre y Apellido son obligatorios.";
        }
        if (fecha == null) {
            return "Error: Debe seleccionar una fecha de nacimiento.";
        }
        if (correo != null && !correo.isEmpty() && !correo.contains("@")) {
            return "Error: El correo debe tener un formato válido.";
        }
        if (tel != null && !tel.isEmpty() && !tel.matches("\\d{9}")) {
            return "Error: El teléfono debe tener 9 dígitos.";
        }
        return null; // Todo OK
    }

    private Paciente crearObjetoPaciente(String dni, String nom, String ape, java.util.Date fecha, 
                                         String sexo, String tel, String dir, String email) {
        Paciente p = new Paciente();
        p.setDni(dni);
        p.setNombres(nom);
        p.setApellidos(ape);
        // Conversión de fecha (Util -> SQL)
        p.setFechaNacimiento(new java.sql.Date(fecha.getTime()));
        p.setSexo(sexo);
        p.setTelefono(tel);
        p.setDireccion(dir);
        p.setCorreo(email);
        p.setEstado(1); // Siempre activo al crear/editar
        return p;
    }
}