/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.seguridad.control;
import app.dentalreyes.core.ConexionMYSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;



public class CrearUsuarioInicial {
  public static void main(String[] args) {
        // DATOS DEL DENTISTA
        String usuario = "dentista";
        String passwordPlana = "123456"; 
        String rol = "Dentista";
        String nombreCompleto = "Gabriel Barrantes";

        // 1. Encriptar usando nuestra nueva clase nativa (Sin librerías externas)
        String passwordHash = SeguridadUtils.encriptarSHA256(passwordPlana);

        System.out.println("Password Plana: " + passwordPlana);
        System.out.println("Password Hash (SHA-256): " + passwordHash);

        // 2. Insertar en BD (Igual que antes)
        try {
            Connection con = new ConexionMYSQL().obtenerConexion();
              
            // Limpiamos usuarios anteriores para evitar duplicados en la prueba
            // con.prepareStatement("DELETE FROM E_USUARIO WHERE nombre_usuario = 'dentista'").executeUpdate(); 
            
            String sql = "INSERT INTO USUARIO (username, passwordHash, rol, nombreCompleto) VALUES (?, ?, ?,?)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, passwordHash); 
            ps.setString(3, rol);
            ps.setString(4,nombreCompleto);
            
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("¡Usuario " + usuario + " creado exitosamente!");
            }
            
            con.close();
        } catch (Exception e) {
            System.err.println("Error al insertar (probablemente el usuario ya existe): " + e.getMessage());
        }
    }  
}
