package app.dentalreyes.seguridad.control;

import app.dentalreyes.entidades.Usuario;

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
        
        // Aquí encriptamos la contraseña antes de enviarla al DAO
        String passwordHash = SeguridadUtils.encriptarSHA256(passwordPlana); 
        
        // Enviamos el HASH al DAO, no el texto plano
        return usuarioDAO.validarLogin(usuario, passwordHash);
    }
}