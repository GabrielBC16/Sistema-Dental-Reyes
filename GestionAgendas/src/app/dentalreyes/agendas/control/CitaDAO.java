/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.agendas.control;

import app.dentalreyes.core.ConexionMYSQL;
import app.dentalreyes.entidades.Cita;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class CitaDAO {
     // SQL para insertar nueva cita
    private static final String SQL_INSERT = 
        "INSERT INTO Cita (idPaciente, idAgenda, fecha, hora, estado) " +
        "VALUES (?, ?, ?, ?, ?)";

    // SQL para eliminar cita
    private static final String SQL_DELETE = 
        "DELETE FROM Cita WHERE idCita = ?";

    // SQL para obtener citas de un paciente
    private static final String SQL_GET_BY_PACIENTE = 
        "SELECT c.*, p.nombres, p.apellidos, p.dni " +
        "FROM Cita c " +
        "INNER JOIN Paciente p ON c.idPaciente = p.idPaciente " +
        "WHERE c.idPaciente = ? AND c.fecha >= CURDATE() " +
        "ORDER BY c.fecha, c.hora";

    // SQL para obtener cita por ID
    private static final String SQL_GET_BY_ID = 
        "SELECT c.*, p.nombres, p.apellidos, p.dni " +
        "FROM Cita c " +
        "INNER JOIN Paciente p ON c.idPaciente = p.idPaciente " +
        "WHERE c.idCita = ?";

    // SQL para verificar si un horario ya tiene cita
    private static final String SQL_CHECK_HORARIO_OCUPADO = 
        "SELECT COUNT(*) FROM Cita WHERE idAgenda = ? AND estado != 'CANCELADA'";

    /**
     * Registra una nueva cita
     */
    public int insertarCita(Cita cita) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdAgendaHorario());
            ps.setDate(3, Date.valueOf(cita.getFecha()));
            ps.setTime(4, Time.valueOf(cita.getHora()));
            ps.setString(5, cita.getEstado());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Retorna el ID generado
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en CitaDAO.insertarCita: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }

        return -1; // Error
    }

    /**
     * Elimina una cita por ID
     */
    public boolean eliminarCita(int idCita) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, idCita);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en CitaDAO.eliminarCita: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos(null, ps, con);
        }
    }

    /**
     * Obtiene todas las citas futuras de un paciente
     * @return 
     */
    public List<Cita> obtenerCitasPorPaciente(int idPaciente) {
        List<Cita> citas = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_GET_BY_PACIENTE);
            ps.setInt(1, idPaciente);

            rs = ps.executeQuery();

            while (rs.next()) {
                citas.add(mapearResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error en CitaDAO.obtenerCitasPorPaciente: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }

        return citas;
    }

    /**
     * Obtiene una cita por ID
     */
    public Cita obtenerCitaPorId(int idCita) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_GET_BY_ID);
            ps.setInt(1, idCita);

            rs = ps.executeQuery();

            if (rs.next()) {
                return mapearResultSet(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error en CitaDAO.obtenerCitaPorId: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }

        return null;
    }

    /**
     * Verifica si un horario ya está ocupado por una cita activa
     */
    public boolean horarioOcupado(int idAgenda) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_CHECK_HORARIO_OCUPADO);
            ps.setInt(1, idAgenda);

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error en CitaDAO.horarioOcupado: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }

        return false;
    }

    /**
     * Mapea ResultSet a E_Cita
     */
    private Cita mapearResultSet(ResultSet rs) throws SQLException {
        Cita cita = new Cita();
        cita.setIdCita(rs.getInt("idCita"));
        cita.setIdPaciente(rs.getInt("idPaciente"));
        cita.setIdAgendaHorario(rs.getInt("idAgenda"));
        cita.setFecha(rs.getDate("fecha").toLocalDate());
        cita.setHora(rs.getTime("hora").toLocalTime());
        cita.setEstado(rs.getString("estado"));
        
        // Datos adicionales del paciente
        //cita.setNombrePaciente(rs.getString("nombres") + " " + rs.getString("apellidos"));
        //cita.setDniPaciente(rs.getString("dni"));
        
        return cita;
    }

    /**
     * Cierra recursos JDBC
     */
    private void cerrarRecursos(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.err.println("Error cerrando recursos: " + e.getMessage());
        }
    }
}
