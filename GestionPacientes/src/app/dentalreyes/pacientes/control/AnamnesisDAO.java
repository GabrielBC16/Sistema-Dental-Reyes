package app.dentalreyes.pacientes.control;

import app.dentalreyes.entidades.Anamnesis;
import app.dentalreyes.core.ConexionMYSQL;

import java.sql.*;

public class AnamnesisDAO {

    // ==========================================================
    // 1. REGISTRAR ANAMNESIS
    // ==========================================================
    public boolean registrarAnamnesis(Anamnesis a) {
        // Agregamos 'bruxismo' que faltaba en el código original
        String sql = "INSERT INTO Anamnesis (" +
                "idHistorial, hipertension, diabetes, problemasCardiacos, " +
                "problemasCoagulacion, enfermedadRespiratoria, alergiaAntibioticos, " +
                "alergiaAnestesia, otrasAlergias, embarazo, fuma, bruxismo, " + 
                "medicacionActual, motivoConsulta, observaciones" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getIdHistorial());

            // Booleanos (0/1)
            ps.setBoolean(2, a.isHipertension());
            ps.setBoolean(3, a.isDiabetes());
            ps.setBoolean(4, a.isProblemasCardiacos());
            ps.setBoolean(5, a.isProblemasCoagulacion());
            ps.setBoolean(6, a.isEnfermedadRespiratoria());
            
            ps.setBoolean(7, a.isAlergiaAntibioticos());
            ps.setBoolean(8, a.isAlergiaAnestesia());
            
            // Textos y otros booleanos
            ps.setString(9, a.getOtrasAlergias());
            ps.setBoolean(10, a.isEmbarazo());
            ps.setBoolean(11, a.isFuma());
            ps.setBoolean(12, a.isBruxismo()); // Nuevo campo
            
            ps.setString(13, a.getMedicacionActual());
            ps.setString(14, a.getMotivoConsulta());
            ps.setString(15, a.getObservaciones());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.out.println("ERROR registrarAnamnesis: " + ex.getMessage());
            return false;
        }
    }

    // ==========================================================
    // 2. OBTENER LA ÚLTIMA ANAMNESIS (Vital para el Historial)
    // ==========================================================
    public Anamnesis obtenerUltimaAnamnesis(int idHistorial) {
        String sql = "SELECT * FROM Anamnesis WHERE idHistorial = ? ORDER BY fechaRegistro DESC LIMIT 1";

        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idHistorial);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearAnamnesis(rs);
            }

        } catch (SQLException ex) {
            System.out.println("ERROR obtenerUltimaAnamnesis: " + ex.getMessage());
        }
        return null;
    }

    // ==========================================================
    // MAPEO (Helper)
    // ==========================================================
    private Anamnesis mapearAnamnesis(ResultSet rs) throws SQLException {
        Anamnesis a = new Anamnesis();

        a.setIdAnamnesis(rs.getInt("idAnamnesis"));
        a.setIdHistorial(rs.getInt("idHistorial"));
        a.setFechaRegistro(rs.getTimestamp("fechaRegistro"));

        // Enfermedades
        a.setHipertension(rs.getBoolean("hipertension"));
        a.setDiabetes(rs.getBoolean("diabetes"));
        a.setProblemasCardiacos(rs.getBoolean("problemasCardiacos"));
        a.setProblemasCoagulacion(rs.getBoolean("problemasCoagulacion"));
        a.setEnfermedadRespiratoria(rs.getBoolean("enfermedadRespiratoria"));

        // Alergias
        a.setAlergiaAntibioticos(rs.getBoolean("alergiaAntibioticos"));
        a.setAlergiaAnestesia(rs.getBoolean("alergiaAnestesia"));
        a.setOtrasAlergias(rs.getString("otrasAlergias"));

        // Hábitos
        a.setEmbarazo(rs.getBoolean("embarazo"));
        a.setFuma(rs.getBoolean("fuma"));
        a.setBruxismo(rs.getBoolean("bruxismo")); // Nuevo

        // Textos
        a.setMedicacionActual(rs.getString("medicacionActual"));
        a.setMotivoConsulta(rs.getString("motivoConsulta"));
        a.setObservaciones(rs.getString("observaciones"));

        return a;
    }
}