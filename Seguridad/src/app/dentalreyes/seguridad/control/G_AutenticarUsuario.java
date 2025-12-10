package app.dentalreyes.seguridad.control;

import app.dentalreyes.entidades.Usuario;
import app.dentalreyes.entidades.UsuarioSesion; // Importar la clase de sesión

public class G_AutenticarUsuario {

    private UsuarioDAO usuarioDAO;

    public G_AutenticarUsuario() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario validarCredenciales(String usuario, String passwordPlana) {
        // Validaciones básicas
        if (usuario == null || usuario.isEmpty() || passwordPlana == null || passwordPlana.isEmpty()) {
            return null;
        }
        
        // Encriptamos la contraseña
        String passwordHash = SeguridadUtils.encriptarSHA256(passwordPlana); 
        
        // Consultamos a la Base de Datos
        Usuario usuarioEncontrado = usuarioDAO.validarLogin(usuario, passwordHash);

        // --- AQUÍ GUARDA LA SESIÓN ---
        if (usuarioEncontrado != null) {
            // Si el login fue exitoso, guardamos los datos en la memoria global
            UsuarioSesion.getInstance().iniciarSesion(
                usuarioEncontrado.getIdUsuario(),
                usuarioEncontrado.getNombreUsuario(),
                usuarioEncontrado.getRol(),
                usuarioEncontrado.getNombreCompleto()
            );
        }
        // ---------------------------------------------

        return usuarioEncontrado;
    }
}