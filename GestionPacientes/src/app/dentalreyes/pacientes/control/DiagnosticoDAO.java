package app.dentalreyes.pacientes.control;

import app.dentalreyes.entidades.Diagnostico;
import app.dentalreyes.core.ConexionMYSQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO {

    // 1. INSERTAR DIAGNÓSTICO
    public boolean insertarDiagnostico(Diagnostico d) {
        String sql = "INSERT INTO Diagnostico " +
                     "(idHistorial, idCita, piezaDental, categoria, descripcion, estado, fechaDiagnostico) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, d.getIdHistorial());
            
            // Manejo de nulos para ID Cita
            if (d.getIdCita() == 0) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(2, d.getIdCita());
            }

            stmt.setString(3, d.getPiezaDental());
            stmt.setString(4, d.getCategoria());
            stmt.setString(5, d.getDescripcion());
            stmt.setString(6, d.getEstado()); // Generalmente entra como 'ACTIVO'

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar Diagnóstico: " + e.getMessage());
            return false;
        }
    }

    // 2. LISTAR POR HISTORIAL (Para llenar la Tabla de la UI)
    public List<Diagnostico> listarPorHistorial(int idHistorial) {
        List<Diagnostico> lista = new ArrayList<>();
        String sql = "SELECT * FROM Diagnostico WHERE idHistorial = ? ORDER BY fechaDiagnostico DESC";

        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHistorial);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearDiagnostico(rs)); // Usamos el método helper
            }

        } catch (SQLException e) {
            System.out.println("Error al listar Diagnósticos: " + e.getMessage());
        }
        return lista;
    }

    // 3. OBTENER POR ID (Para editar)
    public Diagnostico obtenerPorId(int idDiagnostico) {
        String sql = "SELECT * FROM Diagnostico WHERE idDiagnostico = ?";

        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDiagnostico);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearDiagnostico(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener Diagnóstico: " + e.getMessage());
        }
        return null;
    }

    // 4. ACTUALIZAR (Para cambiar estado a 'RESUELTO')
    public boolean actualizarDiagnostico(Diagnostico d) {
        String sql = "UPDATE Diagnostico SET " +
                     "idCita = ?, piezaDental = ?, categoria = ?, " +
                     "descripcion = ?, estado = ? " +
                     "WHERE idDiagnostico = ?";

        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (d.getIdCita() == 0) {
                stmt.setNull(1, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(1, d.getIdCita());
            }

            stmt.setString(2, d.getPiezaDental());
            stmt.setString(3, d.getCategoria());
            stmt.setString(4, d.getDescripcion());
            stmt.setString(5, d.getEstado());
            stmt.setInt(6, d.getIdDiagnostico());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar Diagnóstico: " + e.getMessage());
            return false;
        }
    }

    // 5. ELIMINAR (Si se equivocaron al registrar)
    public boolean eliminarDiagnostico(int idDiagnostico) {
        String sql = "DELETE FROM Diagnostico WHERE idDiagnostico = ?";

        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDiagnostico);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar Diagnóstico: " + e.getMessage());
            return false;
        }
    }

    // 6. CAMBIAR ESTADO A RESUELTO (Rápido)
    public boolean marcarResuelto(int idDiagnostico) {
        String sql = "UPDATE Diagnostico SET estado = 'RESUELTO' WHERE idDiagnostico = ?";
        
        try (Connection conn = ConexionMYSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idDiagnostico);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al resolver diagnóstico: " + e.getMessage());
            return false;
        }
    }
    // HELPER: MAPEAR RESULTSET A OBJETO
    //Evita repetir código y mantiene limpio el DAO

    private Diagnostico mapearDiagnostico(ResultSet rs) throws SQLException {
        Diagnostico d = new Diagnostico();
        d.setIdDiagnostico(rs.getInt("idDiagnostico"));
        d.setIdHistorial(rs.getInt("idHistorial"));
        d.setIdCita(rs.getInt("idCita"));
        d.setPiezaDental(rs.getString("piezaDental"));
        d.setCategoria(rs.getString("categoria"));
        d.setDescripcion(rs.getString("descripcion"));
        d.setEstado(rs.getString("estado"));
        d.setFechaDiagnostico(rs.getTimestamp("fechaDiagnostico"));
        return d;
    }
}