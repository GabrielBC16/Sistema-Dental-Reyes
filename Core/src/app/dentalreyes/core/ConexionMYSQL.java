package app.dentalreyes.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMYSQL {

    // 1. Configuración como constantes estáticas
    private static final String URL = "jdbc:mysql://localhost:3306/dentalreyesdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // 2. Bloque estático: Se ejecuta una sola vez al iniciar la aplicación
    static {
        try {
            // Cargar el driver explícitamente
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[Core] Driver MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.err.println("[Core Error] No se encontró el driver MySQL: " + e.getMessage());
        }
    }

    /**
     * Método ESTÁTICO para obtener la conexión.
     * Se llama así: ConexionMYSQL.getConnection();
     */
    public static Connection getConnection() {
        Connection conexion = null;
        try {
            // Intentar conectar
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            // System.out.println("[Core] Conexion establecida.");
        } catch (SQLException e) {
            System.err.println("[Core Error] Fallo al conectar a la BD: " + e.getMessage());
        }
        return conexion;
    }
}