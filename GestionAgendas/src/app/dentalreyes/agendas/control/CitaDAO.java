package app.dentalreyes.agendas.control;

import app.dentalreyes.core.ConexionMYSQL;
import app.dentalreyes.entidades.Cita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    // ✅ CORRECCIÓN 1: Insertamos SOLO las columnas que SÍ existen en tu BD.
    // Usamos NOW() para la columna 'fechaRegistro'.
    private static final String SQL_INSERT = 
        "INSERT INTO Cita (idPaciente, idAgenda, motivo, estado, fechaRegistro) " +
        "VALUES (?, ?, ?, ?, NOW())";

    private static final String SQL_DELETE = "DELETE FROM Cita WHERE idCita = ?";

    // Para obtener citas, usamos JOIN con AgendaDeHorarios para sacar la fecha/hora
    // ya que la tabla Cita no las tiene directamente.
    private static final String SQL_GET_BY_PACIENTE = 
        "SELECT c.idCita, c.idPaciente, c.idAgenda, c.motivo, c.estado, c.fechaRegistro, " +
        "a.fecha, a.horaInicio as hora, " + // Sacamos fecha/hora de la agenda
        "p.nombres, p.apellidos, p.dni " +
        "FROM Cita c " +
        "INNER JOIN Paciente p ON c.idPaciente = p.idPaciente " +
        "INNER JOIN AgendaDeHorarios a ON c.idAgenda = a.idAgenda " +
        "WHERE c.idPaciente = ? AND a.fecha >= CURDATE() " +
        "ORDER BY a.fecha, a.horaInicio";

    private static final String SQL_CHECK_HORARIO_OCUPADO = 
        "SELECT COUNT(*) FROM Cita WHERE idAgenda = ? AND estado != 'CANCELADA'";

    public int insertarCita(Cita cita) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            // 1. ID Paciente
            ps.setInt(1, cita.getIdPaciente());
            
            // 2. ID Agenda (Aquí está la fecha y hora implícita)
            ps.setInt(2, cita.getIdAgendaHorario());
            
            // 3. Motivo (Ponemos uno por defecto si viene null)
            String motivo = (cita.getMotivo() == null || cita.getMotivo().isEmpty()) 
                            ? "Consulta General" : cita.getMotivo();
            ps.setString(3, motivo);
            
            // 4. Estado
            ps.setString(4, "PENDIENTE");

            int filas = ps.executeUpdate();

            if (filas > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Retornamos el ID generado
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en CitaDAO.insertarCita: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }
        return -1;
    }

    public boolean horarioOcupado(int idAgenda) {
        try (Connection con = ConexionMYSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_CHECK_HORARIO_OCUPADO)) {
            ps.setInt(1, idAgenda);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return false;
    }

    // ... (Métodos de eliminar y obtener quedan igual o similares) ...

    // ✅ CORRECCIÓN 2: Mapeo inteligente
    private Cita mapearResultSet(ResultSet rs) throws SQLException {
        Cita cita = new Cita();
        cita.setIdCita(rs.getInt("idCita"));
        cita.setIdPaciente(rs.getInt("idPaciente"));
        cita.setIdAgendaHorario(rs.getInt("idAgenda"));
        cita.setMotivo(rs.getString("motivo"));
        cita.setEstado(rs.getString("estado"));
        
        // Intentamos leer fecha/hora solo si la query las trajo (JOIN)
        try {
            cita.setFecha(rs.getDate("fecha").toLocalDate());
            cita.setHora(rs.getTime("hora").toLocalTime());
        } catch (SQLException e) {
            // Si la query no trajo fecha/hora (ej: select * simple), no pasa nada
        }
        
        return cita;
    }

    private void cerrarRecursos(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {}
    }
}