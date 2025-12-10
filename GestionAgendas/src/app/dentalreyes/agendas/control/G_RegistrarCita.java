package app.dentalreyes.agendas.control;

import app.dentalreyes.core.ConexionMYSQL;
import app.dentalreyes.entidades.Cita;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;

public class G_RegistrarCita {

    private final CitaDAO citaDAO;
    private final AgendaDAO agendaDAO;

    public G_RegistrarCita() {
        this.citaDAO = new CitaDAO();
        this.agendaDAO = new AgendaDAO();
    }
    
    // Método auxiliar para buscar ID
    public int buscarIdPaciente(String dni) {
        int id = -1;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConexionMYSQL.getConnection();
            String sql = "SELECT idPaciente FROM Paciente WHERE dni = ? AND estado = 1";
            ps = con.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("idPaciente");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return id;
    }

    public String registrarNuevaCita(String dniPaciente, int idAgenda, LocalDate fecha, LocalTime hora) {
        
        // 1. Validaciones
        if (dniPaciente.isEmpty()) return "Error: Ingrese el DNI del paciente.";
        if (idAgenda <= 0) return "Error: Seleccione un horario válido.";

        // 2. Buscar ID Paciente
        int idPaciente = buscarIdPaciente(dniPaciente);
        if (idPaciente == -1) {
            return "Error: Paciente no encontrado con ese DNI.";
        }

        // 3. Validar ocupado
        if (citaDAO.horarioOcupado(idAgenda)) {
            return "ERROR: El horario seleccionado ya fue ganado por otro usuario.";
        }

        // 4. Crear Objeto
        Cita nuevaCita = new Cita();
        nuevaCita.setIdPaciente(idPaciente);
        nuevaCita.setIdAgendaHorario(idAgenda);
        nuevaCita.setEstado("PENDIENTE");

        // 5. Guardar
        int idGenerado = citaDAO.insertarCita(nuevaCita);

        if (idGenerado > 0) {
            agendaDAO.actualizarEstado(idAgenda, "OCUPADO");
            return "Éxito";
        } else {
            return "ERROR: No se pudo guardar en BD.";
        }
    }
}