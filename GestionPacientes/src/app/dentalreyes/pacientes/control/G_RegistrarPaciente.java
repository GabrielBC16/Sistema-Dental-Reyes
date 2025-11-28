package app.dentalreyes.pacientes.control;

import app.dentalreyes.entidades.Paciente;
import java.sql.Date;

public class G_RegistrarPaciente {
    
    private PacienteDAO pacienteDAO;

    public G_RegistrarPaciente() {
        this.pacienteDAO = new PacienteDAO();
    }
    
    // Recibe los datos "crudos" de la vista
    public String registrarPaciente(String dni, String nombres, String apellidos, 
                                    java.util.Date fechaUtil, String sexo, String telefono, 
                                    String direccion, String correo) {
        
        // 1. Validaciones de Negocio
        if (dni == null || dni.trim().length() != 8) {
            return "Error: El DNI debe tener exactamente 8 dígitos numéricos.";
        }
        if (nombres.isEmpty() || apellidos.isEmpty()) {
            return "Error: Nombre y Apellido son obligatorios.";
        }
        if (fechaUtil == null) {
            return "Error: Debe seleccionar una fecha de nacimiento.";
        }
        
        // 2. Convertir fecha de Java (Util) a fecha de SQL
        Date fechaSQL = new Date(fechaUtil.getTime());
        
        // 3. Crear el objeto Entidad
        Paciente nuevoPaciente = new Paciente();
        nuevoPaciente.setDni(dni);
        nuevoPaciente.setNombres(nombres);
        nuevoPaciente.setApellidos(apellidos);
        nuevoPaciente.setFechaNacimiento(fechaSQL);
        nuevoPaciente.setSexo(sexo);
        nuevoPaciente.setTelefono(telefono);
        nuevoPaciente.setDireccion(direccion);
        nuevoPaciente.setCorreo(correo);
        nuevoPaciente.setEstado(1); // Activo
        
        // 4. Guardar
        if (pacienteDAO.registrar(nuevoPaciente)) {
            return "Éxito: Paciente registrado correctamente.";
        } else {
            return "Error: No se pudo guardar. Verifique si el DNI ya existe.";
        }
    }
}