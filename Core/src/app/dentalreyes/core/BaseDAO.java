package app.dentalreyes.core;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseDAO {
    
    protected Connection getConexion() {
        return ConexionMYSQL.getConnection();
    }
    
    // Este método cierra la conexión de forma segura
    protected void cerrar(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }
}