package app.dentalreyes.pacientes.control;

import app.dentalreyes.entidades.HistorialClinico;
import app.dentalreyes.core.ConexionMYSQL;
import java.sql.*;

public class HistoriaClinicaDAO {

    // ============================================================
    // 1. CREAR HISTORIAL (Se llama automáticamente al crear paciente)
    // ============================================================
    public boolean crearHistorialInicial(int idPaciente) {
        String sql = "INSERT INTO HistorialClinico (idPaciente, fechaCreacion, estadoHistorial) VALUES (?, NOW(), 'ACTIVO')";
        
        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idPaciente);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al crear historial inicial: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // 2. OBTENER HISTORIAL POR ID PACIENTE (Para cargar la UI)
    // ============================================================
    public HistorialClinico obtenerPorPaciente(int idPaciente) {
        String sql = "SELECT * FROM HistorialClinico WHERE idPaciente = ?";
        
        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idPaciente);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapearHistorial(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener historial: " + e.getMessage());
        }
        return null;
    }

    // ============================================================
    // 3. ACTUALIZAR DATOS CLÍNICOS (Alertas, Sangre, Notas)
    // ============================================================
    public boolean actualizarDatosClinicos(HistorialClinico h) {
        String sql = "UPDATE HistorialClinico SET " +
                     "antecedentes = ?, observaciones = ?, grupoSanguineo = ?, " +
                     "firmaConsentimiento = ?, estadoHistorial = ?, alertasMedicas = ? " +
                     "WHERE idHistorial = ?";
        
        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, h.getAntecedentes());
            ps.setString(2, h.getObservaciones());
            ps.setString(3, h.getGrupoSanguineo());
            ps.setBoolean(4, h.isFirmaConsentimiento());
            ps.setString(5, h.getEstadoHistorial());
            ps.setString(6, h.getAlertasMedicas());
            ps.setInt(7, h.getIdHistorial());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar datos clínicos: " + e.getMessage());
            return false;
        }
    }

    // --- HELPER MAPEO ---
    private HistorialClinico mapearHistorial(ResultSet rs) throws SQLException {
        HistorialClinico h = new HistorialClinico();
        h.setIdHistorial(rs.getInt("idHistorial"));
        h.setIdPaciente(rs.getInt("idPaciente"));
        h.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
        h.setAntecedentes(rs.getString("antecedentes"));
        h.setObservaciones(rs.getString("observaciones"));
        h.setGrupoSanguineo(rs.getString("grupoSanguineo"));
        h.setFirmaConsentimiento(rs.getBoolean("firmaConsentimiento"));
        h.setEstadoHistorial(rs.getString("estadoHistorial"));
        h.setAlertasMedicas(rs.getString("alertasMedicas"));
        return h;
    }
}