/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dentalreyes.seguridad.control;

import app.dentalreyes.core.ConexionMYSQL;
import app.dentalreyes.entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class G_AuntenticarUsuario {
    public Usuario validarCredenciales(String usuario, String passwordPlana) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario usuarioEncontrado = null;

        try {
            // 1. Obtenemos conexión del módulo Core
            con = new ConexionMYSQL().obtenerConexion();

            // 2. Preparamos la consulta (Solo buscamos por nombre de usuario)
            String sql = "SELECT id_usuario, nombre_usuario, contrasena_hash, rol " +
                         "FROM E_USUARIO WHERE nombre_usuario = ?";
            
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();

            // 3. Verificamos si existe el usuario
          if (rs.next()) {
                String hashEnBD = rs.getString("contrasena_hash");

                // Encriptamos la contraseña que acaba de escribir el usuario
                String hashIngresado = SeguridadUtils.encriptarSHA256(passwordPlana);

                // Comparamos los textos (String vs String)
                if (hashEnBD.equals(hashIngresado)) {
                    // ¡Coinciden!
                    usuarioEncontrado = new Usuario();
                    usuarioEncontrado.setIdUsuario(rs.getInt("id_usuario"));
                    usuarioEncontrado.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuarioEncontrado.setRol(rs.getString("rol"));
                }
            }

        } catch (SQLException e) {
            System.err.println("[Seguridad] Error al autenticar: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {}
        }

        return usuarioEncontrado; // Retorna null si no existe o pass incorrecta
    }
}
