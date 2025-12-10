package app.dentalreyes.pacientes.control;

import app.dentalreyes.entidades.Intervencion;
import app.dentalreyes.core.ConexionMYSQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntervencionDAO {

    // ============================================================
    // 1. REGISTRAR INTERVENCIÓN (Tratamiento)
    // ============================================================
    public boolean registrarIntervencion(Intervencion i) {
        String sql = "INSERT INTO Intervencion " +
                     "(idHistorial, idCita, piezaDental, tratamiento, descripcion, " +
                     "observaciones, costo, estado, fechaIntervencion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, i.getIdHistorial());

            // Manejo de Cita Nula (Opcional)
            if (i.getIdCita() == 0) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, i.getIdCita());
            }

            ps.setString(3, i.getPiezaDental());
            ps.setString(4, i.getTratamiento());
            ps.setString(5, i.getDescripcion());
            ps.setString(6, i.getObservaciones());
            ps.setDouble(7, i.getCosto());
            ps.setString(8, i.getEstado()); // 'REALIZADO' o 'PLANIFICADO'

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar intervención: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // 2. LISTAR POR HISTORIAL (Para la Pestaña 3 del Dashboard)
    // ============================================================
    public List<Intervencion> listarPorHistorial(int idHistorial) {
        List<Intervencion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Intervencion WHERE idHistorial = ? ORDER BY fechaIntervencion DESC";

        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idHistorial);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapearIntervencion(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar intervenciones: " + e.getMessage());
        }
        return lista;
    }

    // ============================================================
    // 3. CAMBIAR ESTADO (De Planificado a Realizado)
    // ============================================================
    public boolean actualizarEstado(int idIntervencion, String nuevoEstado) {
        String sql = "UPDATE Intervencion SET estado = ? WHERE idIntervencion = ?";
        
        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idIntervencion);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado intervención: " + e.getMessage());
            return false;
        }
    }

    // --- HELPER MAPEO ---
    private Intervencion mapearIntervencion(ResultSet rs) throws SQLException {
        Intervencion i = new Intervencion();
        i.setIdIntervencion(rs.getInt("idIntervencion"));
        i.setIdHistorial(rs.getInt("idHistorial"));
        i.setIdCita(rs.getInt("idCita")); // Si es null en BD, Java devuelve 0
        i.setPiezaDental(rs.getString("piezaDental"));
        i.setTratamiento(rs.getString("tratamiento"));
        i.setDescripcion(rs.getString("descripcion"));
        i.setObservaciones(rs.getString("observaciones"));
        i.setCosto(rs.getDouble("costo"));
        i.setEstado(rs.getString("estado"));
        i.setFechaIntervencion(rs.getTimestamp("fechaIntervencion"));
        return i;
    }
}