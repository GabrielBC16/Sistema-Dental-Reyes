
package app.dentalreyes.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMYSQL{
    
    public ConexionMYSQL(){
        try {
            // Cargar el driver explícitamente (útil para versiones antiguas de Java)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[Core] Driver MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("[Core Error] No se encontró el driver MySQL: " + e.getMessage());
            System.err.println("Solución: Agregar mysql-connector-j.jar al proyecto");
        }
    }
    
    String url = "jdbc:mysql://localhost:3306/dentalreyesdb";
    String user = "root";
    String password = "root";

   Connection conexion = null;
    public Connection obtenerConexion() {
        
        try {            
            // Intentar conectar
            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("[Core] Conexion correcta.");
            System.out.println("Miraaa");
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
