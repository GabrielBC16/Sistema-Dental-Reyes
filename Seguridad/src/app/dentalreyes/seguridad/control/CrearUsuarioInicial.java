package app.dentalreyes.seguridad.control;

import app.dentalreyes.core.ConexionMYSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrearUsuarioInicial {

    public static void main(String[] args) {
        // 1. Crear al Dentista
        crearUsuario("dentista2", "123456", "Dentista", "Dr. Gabriel Barrantes");

        // 2. Crear al Asistente
        crearUsuario("asistente2", "123456", "Asistente", "Lic. Ana López");
    }

    /**
     * Método auxiliar para registrar usuarios en la base de datos.
     */
    private static void crearUsuario(String username, String passwordPlana, String rol, String nombreCompleto) {
        
        // 1. Encriptar contraseña
        String passwordHash = SeguridadUtils.encriptarSHA256(passwordPlana);

        System.out.println("--- Creando Usuario: " + username + " (" + rol + ") ---");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConexionMYSQL.getConnection();
            
            // Query de inserción
            String sql = "INSERT INTO USUARIO (username, passwordHash, rol, nombreCompleto, estado) VALUES (?, ?, ?, ?, 1)";
            
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setString(3, rol);
            ps.setString(4, nombreCompleto);
            
            int filas = ps.executeUpdate();
            
            if (filas > 0) {
                System.out.println("✅ ¡Usuario " + username + " creado exitosamente!");
            }
            
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Código de error para Duplicate Entry
                System.err.println("⚠️ El usuario '" + username + "' ya existe en la base de datos.");
            } else {
                System.err.println("❌ Error al insertar: " + e.getMessage());
            }
        } finally {
            // Cerrar recursos manualmente aquí porque es una clase 'main' suelta
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) { }
        }
        System.out.println("----------------------------------------------------");
    }
}