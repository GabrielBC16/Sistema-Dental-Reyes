/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.agendas.control;


import app.dentalreyes.core.ConexionMYSQL;
import app.dentalreyes.entidades.Agenda_Horarios;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {
      // SQL para insertar nueva disponibilidad
    private static final String SQL_INSERT = 
        "INSERT INTO AgendaDeHorarios (fecha, horaInicio, horaFin, estado) " +
        "VALUES (?, ?, ?, ?)";

    // SQL para obtener horarios de una semana
    private static final String SQL_GET_SEMANA = 
        "SELECT * FROM AgendaDeHorarios " +
        "WHERE fecha BETWEEN ? AND ? " +
        "ORDER BY fecha, horaInicio";

    // SQL para verificar si existe conflicto
    private static final String SQL_CHECK_CONFLICT = 
        "SELECT COUNT(*) FROM AgendaDeHorarios " +
        "WHERE fecha = ? AND horaInicio = ? ";

    // SQL para actualizar estado
    private static final String SQL_UPDATE_ESTADO = 
        "UPDATE AgendaDeHorarios SET estado = ? WHERE idAgenda = ?";

    // SQL para obtener horarios disponibles de un día
    private static final String SQL_GET_DISPONIBLES_DIA = 
        "SELECT * FROM AgendaDeHorarios " +
        "WHERE fecha = ? AND estado = 'DISPONIBLE' " +
        "ORDER BY horaInicio";

    /**
     * Registra un nuevo bloque de disponibilidad horaria
     */
    public boolean insertarDisponibilidad(Agenda_Horarios agenda) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_INSERT);

            ps.setDate(1, Date.valueOf(agenda.getDia()));
            ps.setTime(2, Time.valueOf(agenda.getHoraInicio()));
            ps.setTime(3, Time.valueOf(agenda.getHoraFin()));
            ps.setString(4, agenda.getEstado());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error en AgendaDAO.insertarDisponibilidad: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos(null, ps, con);
        }
    }

    /**
     * Obtiene todos los horarios de una semana específica
     */
    public List<Agenda_Horarios> obtenerHorariosSemana(LocalDate fechaInicio, 
                                                           LocalDate fechaFin
                                                          ) {
        List<Agenda_Horarios> horarios = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_GET_SEMANA);

            ps.setDate(1, Date.valueOf(fechaInicio));
            ps.setDate(2, Date.valueOf(fechaFin));

            rs = ps.executeQuery();

            while (rs.next()) {
                horarios.add(mapearResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error en AgendaDAO.obtenerHorariosSemana: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }

        return horarios;
    }

    /**
     * Verifica si existe conflicto en un horario
     */
    public boolean existeConflicto(LocalDate fecha, LocalTime horaInicio) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_CHECK_CONFLICT);

            ps.setDate(1, Date.valueOf(fecha));
            ps.setTime(2, Time.valueOf(horaInicio));

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error en AgendaDAO.existeConflicto: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }

        return false;
    }

    /**
     * Actualiza el estado de un horario (DISPONIBLE -> OCUPADO o viceversa)
     */
    public boolean actualizarEstado(int idAgenda, String nuevoEstado) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_UPDATE_ESTADO);

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idAgenda);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en AgendaDAO.actualizarEstado: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos(null, ps, con);
        }
    }

    /**
     * Obtiene horarios disponibles de un día específico
     */
    public List<Agenda_Horarios> obtenerDisponiblesDia(LocalDate fecha) {
        List<Agenda_Horarios> disponibles = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_GET_DISPONIBLES_DIA);

            ps.setDate(1, Date.valueOf(fecha));

            rs = ps.executeQuery();

            while (rs.next()) {
                disponibles.add(mapearResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error en AgendaDAO.obtenerDisponiblesDia: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps, con);
        }

        return disponibles;
    }

    /**
     * Mapea un ResultSet a un objeto E_AgendaDeHorarios
     */
    private Agenda_Horarios mapearResultSet(ResultSet rs) throws SQLException {
        Agenda_Horarios agenda = new Agenda_Horarios();
        agenda.setDia(rs.getDate("fecha").toLocalDate());
        agenda.setHoraInicio(rs.getTime("horaInicio").toLocalTime());
        agenda.setHoraFin(rs.getTime("horaFin").toLocalTime());
        agenda.setEstado(rs.getString("estado"));
        return agenda;
    }

    /**
     * Cierra recursos JDBC de forma segura
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
