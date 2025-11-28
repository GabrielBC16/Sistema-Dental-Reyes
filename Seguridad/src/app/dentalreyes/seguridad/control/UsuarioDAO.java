package app.dentalreyes.seguridad.control;

import app.dentalreyes.core.ConexionMYSQL;
import app.dentalreyes.entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // SQL para buscar usuario activo (estado = 1)
    private static final String SQL_LOGIN = 
        "SELECT idUsuario, username, passwordHash, rol, nombreCompleto " +
        "FROM Usuario WHERE username = ? AND passwordHash = ? AND estado = 1";

    public Usuario validarLogin(String user, String pass) {
        Usuario usuarioEncontrado = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 1. Usamos la conexión estática directa
            con = ConexionMYSQL.getConnection();
            ps = con.prepareStatement(SQL_LOGIN);
            
            ps.setString(1, user);
            ps.setString(2, pass); // Nota: En producción esto debería ser el hash
            
            rs = ps.executeQuery();
            
            if (rs.next()) {
                // 2. Mapeamos los datos de MySQL a la Entidad Java
                usuarioEncontrado = new Usuario();
                usuarioEncontrado.setIdUsuario(rs.getInt("idUsuario"));
                usuarioEncontrado.setNombreUsuario(rs.getString("username"));
                usuarioEncontrado.setContrasenaHash(rs.getString("passwordHash"));
                usuarioEncontrado.setRol(rs.getString("rol"));
                usuarioEncontrado.setNombreCompleto(rs.getString("nombreCompleto"));
                usuarioEncontrado.setEstado(1);
            }

        } catch (SQLException e) {
            System.err.println("Error en UsuarioDAO: " + e.getMessage());
        } finally {
            // 3. Cierre manual de recursos si no usas BaseDAO
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) { }
        }
        return usuarioEncontrado;
    }
}