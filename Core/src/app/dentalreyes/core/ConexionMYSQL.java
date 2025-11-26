
package app.dentalreyes.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMYSQL {
    String url = "jdbc:mysql://localhost:3306/dentalreyesdb";
    String user = "root";
    String password = "root";

   Connection conexion = null;
    public Connection obtenerConexion() {
        
        try {            
            // Intentar conectar
            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("[Core] Conexion correcta.");
            return conexion;
            
        } catch (SQLException e) {
            System.err.println("[Core Error] Fallo al conectar a la BD: " + e.getMessage());
        }
        return null;
    }

    /**
     * Cierra la conexión activa 
     */
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("[Core] Conexion cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("[Core Error] No se pudo cerrar la conexion: " + e.getMessage());
        }
    }
}
